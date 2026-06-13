package vn.edu.nlu.fit.mythuatshop.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.edu.nlu.fit.mythuatshop.Dao.InventoryDao;
import vn.edu.nlu.fit.mythuatshop.Model.InventoryReportRow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InventoryExcelReportService {
    private static final String FILE_NAME = "bao-cao-ton-kho.xlsx";
    private static final String TEMPLATE_SHEET = "Template";
    private static final int HEADER_ROW_INDEX = 4;
    private static final int DATA_START_ROW_INDEX = 5;

    private final InventoryDao inventoryDao;
    private final Path reportFolder;
    private final Path reportPath;

    public InventoryExcelReportService() {
        this.inventoryDao = new InventoryDao();
        this.reportFolder = Paths.get("D:\\excel_tonkho");
        this.reportPath = reportFolder.resolve(FILE_NAME);
    }

    public synchronized void createReportFileIfMissing() throws IOException {
        Files.createDirectories(reportFolder);
        try (Workbook workbook = openOrCreateWorkbook()) {
            createTemplateIfMissing(workbook);
            try (OutputStream outputStream = Files.newOutputStream(reportPath)) {
                workbook.write(outputStream);
            }
        }
    }
    public Path getReportPath() {
        return reportPath;
    }
    public boolean reportFileExists() {
        return Files.exists(reportPath);
    }
    private Workbook openOrCreateWorkbook() throws IOException {
        if (Files.exists(reportPath)) {
            try (InputStream inputStream = Files.newInputStream(reportPath)) {
                return WorkbookFactory.create(inputStream);
            }
        }

        return new XSSFWorkbook();
    }
    private void createTemplateIfMissing(Workbook workbook) {
        Sheet templateSheet = workbook.getSheet(TEMPLATE_SHEET);

        if (templateSheet == null) {
            templateSheet = workbook.createSheet(TEMPLATE_SHEET);
            createTemplateContent(workbook, templateSheet);
        }

        workbook.setSheetOrder(TEMPLATE_SHEET, 0);
    }
    private void createTemplateContent(Workbook workbook, Sheet sheet) {
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle labelStyle = createLabelStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO TỒN KHO SẢN PHẨM");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));

        Row dateRow = sheet.createRow(1);
        Cell dateLabelCell = dateRow.createCell(0);
        dateLabelCell.setCellValue("Ngày báo cáo:");
        dateLabelCell.setCellStyle(labelStyle);

        Row updateRow = sheet.createRow(2);
        Cell updateLabelCell = updateRow.createCell(0);
        updateLabelCell.setCellValue("Lần cập nhật gần nhất:");
        updateLabelCell.setCellStyle(labelStyle);

        Row headerRow = sheet.createRow(HEADER_ROW_INDEX);

        String[] headers = {
                "STT",
                "Mã sản phẩm",
                "Tên sản phẩm",
                "Danh mục",
                "Thương hiệu",
                "Giá gốc",
                "Giảm giá (%)",
                "Giá sau giảm",
                "Tồn kho hiện tại",
                "Đã bán hiện tại",
                "Tổng nhập kho",
                "Tổng bán lịch sử",
                "Hoàn kho do hủy đơn",
                "Điều chỉnh kho",
                "Trạng thái kho",
                "Trạng thái bán",
                "Giá trị tồn kho",
                "Cập nhật kho gần nhất"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 4500);
        }
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(17, 6000);
        sheet.createFreezePane(0, 5);
    }
    private CellStyle createTitleStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    private CellStyle createLabelStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
    private CellStyle createHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(style);
        return style;
    }
    private void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    public synchronized void updateTodayReport() throws IOException {
        List<InventoryReportRow> rows = inventoryDao.findInventoryReportRows();

        Files.createDirectories(reportFolder);

        try (Workbook workbook = openOrCreateWorkbook()) {
            createTemplateIfMissing(workbook);

            String todaySheetName = LocalDate.now().toString();

            Sheet todaySheet = createTodaySheet(workbook, todaySheetName);

            writeReportInfo(todaySheet);
            writeReportData(workbook, todaySheet, rows);

            try (OutputStream outputStream = Files.newOutputStream(reportPath)) {
                workbook.write(outputStream);
            }
        }
    }

    private Sheet createTodaySheet(Workbook workbook, String sheetName) {
        int oldIndex = workbook.getSheetIndex(sheetName);

        if (oldIndex >= 0) {
            workbook.removeSheetAt(oldIndex);
        }

        int templateIndex = workbook.getSheetIndex(TEMPLATE_SHEET);
        Sheet sheet = workbook.cloneSheet(templateIndex);

        int newIndex = workbook.getNumberOfSheets() - 1;
        workbook.setSheetName(newIndex, sheetName);

        return workbook.getSheet(sheetName);
    }
    private void writeReportInfo(Sheet sheet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Row dateRow = getOrCreateRow(sheet, 1);
        Cell dateCell = dateRow.getCell(1);
        if (dateCell == null) {
            dateCell = dateRow.createCell(1);
        }
        dateCell.setCellValue(LocalDate.now().toString());

        Row updateRow = getOrCreateRow(sheet, 2);
        Cell updateCell = updateRow.getCell(1);
        if (updateCell == null) {
            updateCell = updateRow.createCell(1);
        }
        updateCell.setCellValue(LocalDateTime.now().format(formatter));
    }

    private void writeReportData(Workbook workbook, Sheet sheet, List<InventoryReportRow> rows) {
        CellStyle textStyle = createTextStyle(workbook);
        CellStyle numberStyle = createNumberStyle(workbook);
        CellStyle moneyStyle = createMoneyStyle(workbook);

        int rowIndex = DATA_START_ROW_INDEX;
        int stt = 1;

        for (InventoryReportRow item : rows) {
            Row row = sheet.createRow(rowIndex);

            createCell(row, 0, stt++, numberStyle);
            createCell(row, 1, item.getProductCode(), textStyle);
            createCell(row, 2, item.getProductName(), textStyle);
            createCell(row, 3, item.getCategoryName(), textStyle);
            createCell(row, 4, item.getBrand(), textStyle);
            createCell(row, 5, item.getPrice(), moneyStyle);
            createCell(row, 6, item.getDiscountDefault(), numberStyle);
            createCell(row, 7, item.getPriceAfterDiscount(), moneyStyle);
            createCell(row, 8, item.getQuantityStock(), numberStyle);
            createCell(row, 9, item.getSoldQuantity(), numberStyle);
            createCell(row, 10, item.getTotalImported(), numberStyle);
            createCell(row, 11, item.getTotalSale(), numberStyle);
            createCell(row, 12, item.getTotalCancel(), numberStyle);
            createCell(row, 13, item.getTotalAdjust(), numberStyle);
            createCell(row, 14, item.getStockStatus(), textStyle);
            createCell(row, 15, item.getActiveStatus(), textStyle);
            createCell(row, 16, item.getStockValue(), moneyStyle);

            String lastUpdate = "";
            if (item.getLastTransactionAt() != null) {
                lastUpdate = item.getLastTransactionAt().toString();
            }
            createCell(row, 17, lastUpdate, textStyle);
            rowIndex++;
        }
    }

    private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private Row getOrCreateRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }

    private CellStyle createTextStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        setBorder(style);
        return style;
    }

    private CellStyle createNumberStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        setBorder(style);
        return style;
    }

    private CellStyle createMoneyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        setBorder(style);
        return style;
    }
}
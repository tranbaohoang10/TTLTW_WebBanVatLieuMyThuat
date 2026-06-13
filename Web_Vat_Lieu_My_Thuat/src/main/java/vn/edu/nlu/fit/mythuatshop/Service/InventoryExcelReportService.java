package vn.edu.nlu.fit.mythuatshop.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class InventoryExcelReportService {
    private static final String FILE_NAME = "bao-cao-ton-kho.xlsx";
    private static final String TEMPLATE_SHEET = "Template";
    private static final int HEADER_ROW_INDEX = 4;

    private final Path reportFolder;
    private final Path reportPath;

    public InventoryExcelReportService() {
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
}

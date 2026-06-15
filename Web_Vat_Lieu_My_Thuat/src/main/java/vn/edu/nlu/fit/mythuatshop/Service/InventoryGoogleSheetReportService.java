package vn.edu.nlu.fit.mythuatshop.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import vn.edu.nlu.fit.mythuatshop.Dao.InventoryDao;
import vn.edu.nlu.fit.mythuatshop.Model.InventoryReportRow;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InventoryGoogleSheetReportService {
    private static final String APPLICATION_NAME = "MyThuatShop Inventory Report";

    private static final String SPREADSHEET_ID = "15voIjtZRvKfAbp9WV8m-aFGtRiDZZ1X_OplNEPoHneo";
    private static final String SPREADSHEET_URL = "https://docs.google.com/spreadsheets/d/15voIjtZRvKfAbp9WV8m-aFGtRiDZZ1X_OplNEPoHneo/edit";
    private static final String CREDENTIALS_PATH = System.getenv("GOOGLE_CREDENTIALS_PATH");

    private static final String TEMPLATE_SHEET = "Template";
    private static final int HEADER_ROW_INDEX = 4;
    private static final int TOTAL_COLUMNS = 18;
    private static final int WARNING_COLUMN_INDEX = 9;
    private static final int STOCK_VALUE_COLUMN_INDEX = 16;
    private static final int LAST_UPDATE_COLUMN_INDEX = 17;

    private final InventoryDao inventoryDao;

    public InventoryGoogleSheetReportService() {
        this.inventoryDao = new InventoryDao();
    }

    public String getSpreadsheetUrl() {
        return SPREADSHEET_URL;
    }

    public synchronized void updateTodayReport() throws IOException, GeneralSecurityException {
        List<InventoryReportRow> rows = inventoryDao.findInventoryReportRows();

        Sheets sheetsService = createSheetsService();

        createTemplateSheetIfMissing(sheetsService);

        String todaySheetName = LocalDate.now().toString();

        createTodaySheetIfMissing(sheetsService, todaySheetName);

        clearTodaySheet(sheetsService, todaySheetName);

        writeReportData(sheetsService, todaySheetName, rows);
    }

    private Sheets createSheetsService() throws IOException, GeneralSecurityException {
        if (CREDENTIALS_PATH == null || CREDENTIALS_PATH.trim().isEmpty()) {
            throw new IOException("Chưa cấu hình biến môi trường GOOGLE_CREDENTIALS_PATH trên server");
        }
        GoogleCredentials credentials;

        try (FileInputStream inputStream = new FileInputStream(CREDENTIALS_PATH)) {
            credentials = GoogleCredentials.fromStream(inputStream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        }

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(APPLICATION_NAME).build();
    }

    private void createTemplateSheetIfMissing(Sheets sheetsService) throws IOException {
        if (sheetExists(sheetsService, TEMPLATE_SHEET)) {
            writeTemplateData(sheetsService);
            return;
        }

        AddSheetRequest addSheetRequest = new AddSheetRequest()
                .setProperties(new SheetProperties()
                        .setTitle(TEMPLATE_SHEET)
                        .setIndex(0));

        BatchUpdateSpreadsheetRequest request = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(
                        new Request().setAddSheet(addSheetRequest)
                ));

        sheetsService.spreadsheets()
                .batchUpdate(SPREADSHEET_ID, request)
                .execute();

        writeTemplateData(sheetsService);
    }

    private void createTodaySheetIfMissing(Sheets sheetsService, String todaySheetName) throws IOException {
        if (sheetExists(sheetsService, todaySheetName)) {
            return;
        }

        AddSheetRequest addSheetRequest = new AddSheetRequest()
                .setProperties(new SheetProperties()
                        .setTitle(todaySheetName));

        BatchUpdateSpreadsheetRequest request = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(
                        new Request().setAddSheet(addSheetRequest)
                ));

        sheetsService.spreadsheets()
                .batchUpdate(SPREADSHEET_ID, request)
                .execute();
    }

    private boolean sheetExists(Sheets sheetsService, String sheetName) throws IOException {
        Spreadsheet spreadsheet = sheetsService.spreadsheets()
                .get(SPREADSHEET_ID)
                .execute();

        for (Sheet sheet : spreadsheet.getSheets()) {
            String title = sheet.getProperties().getTitle();

            if (sheetName.equals(title)) {
                return true;
            }
        }

        return false;
    }

    private void writeTemplateData(Sheets sheetsService) throws IOException {
        List<List<Object>> values = createReportLayout(Collections.emptyList(), true);

        ValueRange body = new ValueRange().setValues(values);

        sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "'" + TEMPLATE_SHEET + "'!A1", body)
                .setValueInputOption("RAW")
                .execute();
        formatReportSheet(sheetsService, TEMPLATE_SHEET, values.size(), Collections.emptyList());
    }

    private void clearTodaySheet(Sheets sheetsService, String sheetName) throws IOException {
        ClearValuesRequest clearRequest = new ClearValuesRequest();

        sheetsService.spreadsheets().values()
                .clear(SPREADSHEET_ID, "'" + sheetName + "'!A1:Z5000", clearRequest)
                .execute();
    }

    private void writeReportData(Sheets sheetsService, String sheetName, List<InventoryReportRow> rows)
            throws IOException {
        List<List<Object>> values = createReportLayout(rows, false);

        ValueRange body = new ValueRange().setValues(values);

        sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "'" + sheetName + "'!A1", body)
                .setValueInputOption("RAW")
                .execute();
        formatReportSheet(sheetsService, sheetName, values.size(), rows);
    }

    private List<List<Object>> createReportLayout(List<InventoryReportRow> rows, boolean onlyTemplate) {
        List<List<Object>> values = new ArrayList<>();

        values.add(Collections.singletonList("BÁO CÁO TỒN KHO SẢN PHẨM"));

        if (onlyTemplate) {
            values.add(Arrays.asList("Ngày báo cáo:", ""));
            values.add(Arrays.asList("Lần cập nhật gần nhất:", ""));
        } else {
            values.add(Arrays.asList("Ngày báo cáo:", LocalDate.now().toString()));
            values.add(Arrays.asList(
                    "Lần cập nhật gần nhất:",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            ));
        }

        values.add(Collections.emptyList());

        values.add(Arrays.asList(
                "STT",
                "Mã sản phẩm",
                "Tên sản phẩm",
                "Danh mục",
                "Thương hiệu",
                "Giá gốc",
                "Giảm giá (%)",
                "Giá sau giảm",
                "Tồn kho hiện tại",
                "Cảnh báo",
                "Đã bán hiện tại",
                "Tổng nhập kho",
                "Tổng bán lịch sử",
                "Hoàn kho do hủy đơn",
                "Điều chỉnh kho",
                "Trạng thái bán",
                "Giá trị tồn kho",
                "Cập nhật kho gần nhất"
        ));

        if (onlyTemplate) {
            return values;
        }

        int stt = 1;

        for (InventoryReportRow item : rows) {
            values.add(Arrays.asList(
                    stt++,
                    item.getProductId(),
                    safe(item.getProductName()),
                    safe(item.getCategoryName()),
                    safe(item.getBrand()),
                    item.getPrice(),
                    item.getDiscountDefault(),
                    item.getPriceAfterDiscount(),
                    item.getQuantityStock(),
                    item.getStockStatus(),
                    item.getSoldQuantity(),
                    item.getTotalImported(),
                    item.getTotalSale(),
                    item.getTotalCancel(),
                    item.getTotalAdjust(),

                    item.getActiveStatus(),
                    item.getStockValue(),
                    item.getLastTransactionAt() == null
                            ? ""
                            : item.getLastTransactionAt()
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            ));
        }
        return values;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
    private void formatReportSheet(Sheets sheetsService, String sheetName, int rowCount, List<InventoryReportRow> rows) throws IOException {
        Integer sheetId = getSheetIdByName(sheetsService, sheetName);
        if (sheetId == null) {
            return;
        }
        List<Request> requests = new ArrayList<>();
        requests.add(new Request().setUpdateSheetProperties(
                new UpdateSheetPropertiesRequest()
                        .setProperties(new SheetProperties()
                                .setSheetId(sheetId)
                                .setGridProperties(new GridProperties()
                                        .setFrozenRowCount(HEADER_ROW_INDEX + 1)))
                        .setFields("gridProperties.frozenRowCount")
        ));
        CellFormat headerFormat = new CellFormat()
                .setBackgroundColor(new Color()
                        .setRed(0.2f)
                        .setGreen(0.4f)
                        .setBlue(0.7f))
                .setTextFormat(new TextFormat()
                        .setBold(true)
                        .setForegroundColor(new Color()
                                .setRed(1f)
                                .setGreen(1f)
                                .setBlue(1f)))
                .setHorizontalAlignment("CENTER");
        requests.add(new Request().setRepeatCell(
                new RepeatCellRequest()
                        .setRange(new GridRange()
                                .setSheetId(sheetId)
                                .setStartRowIndex(HEADER_ROW_INDEX)
                                .setEndRowIndex(HEADER_ROW_INDEX + 1)
                                .setStartColumnIndex(0)
                                .setEndColumnIndex(TOTAL_COLUMNS))
                        .setCell(new CellData().setUserEnteredFormat(headerFormat))
                        .setFields("userEnteredFormat(backgroundColor,textFormat,horizontalAlignment)")
        ));
        requests.add(new Request().setSetBasicFilter(
                new SetBasicFilterRequest()
                        .setFilter(new BasicFilter()
                                .setRange(new GridRange()
                                        .setSheetId(sheetId)
                                        .setStartRowIndex(HEADER_ROW_INDEX)
                                        .setEndRowIndex(rowCount)
                                        .setStartColumnIndex(0)
                                        .setEndColumnIndex(TOTAL_COLUMNS)))
        ));
        for (int i = 0; i < rows.size(); i++) {
            InventoryReportRow item = rows.get(i);

            int rowIndex = HEADER_ROW_INDEX + 1 + i;

            CellFormat warningFormat = new CellFormat()
                    .setBackgroundColor(getWarningBackgroundColor(item))
                    .setTextFormat(new TextFormat()
                            .setBold(true)
                            .setForegroundColor(getWarningTextColor(item)))
                    .setHorizontalAlignment("CENTER");

            requests.add(new Request().setRepeatCell(
                    new RepeatCellRequest()
                            .setRange(new GridRange()
                                    .setSheetId(sheetId)
                                    .setStartRowIndex(rowIndex)
                                    .setEndRowIndex(rowIndex + 1)
                                    .setStartColumnIndex(WARNING_COLUMN_INDEX)
                                    .setEndColumnIndex(WARNING_COLUMN_INDEX + 1))
                            .setCell(new CellData().setUserEnteredFormat(warningFormat))
                            .setFields("userEnteredFormat(backgroundColor,textFormat,horizontalAlignment)")
            ));
        }
        CellFormat stockValueFormat = new CellFormat()
                .setNumberFormat(new NumberFormat()
                        .setType("NUMBER")
                        .setPattern("#,##0"));

        requests.add(new Request().setRepeatCell(
                new RepeatCellRequest()
                        .setRange(new GridRange()
                                .setSheetId(sheetId)
                                .setStartRowIndex(HEADER_ROW_INDEX + 1)
                                .setEndRowIndex(rowCount)
                                .setStartColumnIndex(STOCK_VALUE_COLUMN_INDEX)
                                .setEndColumnIndex(STOCK_VALUE_COLUMN_INDEX + 1))
                        .setCell(new CellData().setUserEnteredFormat(stockValueFormat))
                        .setFields("userEnteredFormat.numberFormat")
        ));

        CellFormat lastUpdateFormat = new CellFormat()
                .setNumberFormat(new NumberFormat()
                        .setType("TEXT"));

        requests.add(new Request().setRepeatCell(
                new RepeatCellRequest()
                        .setRange(new GridRange()
                                .setSheetId(sheetId)
                                .setStartRowIndex(HEADER_ROW_INDEX + 1)
                                .setEndRowIndex(rowCount)
                                .setStartColumnIndex(LAST_UPDATE_COLUMN_INDEX)
                                .setEndColumnIndex(LAST_UPDATE_COLUMN_INDEX + 1))
                        .setCell(new CellData().setUserEnteredFormat(lastUpdateFormat))
                        .setFields("userEnteredFormat.numberFormat")
        ));
        requests.add(new Request().setAutoResizeDimensions(
                new AutoResizeDimensionsRequest()
                        .setDimensions(new DimensionRange()
                                .setSheetId(sheetId)
                                .setDimension("COLUMNS")
                                .setStartIndex(0)
                                .setEndIndex(TOTAL_COLUMNS))
        ));
        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        sheetsService.spreadsheets()
                .batchUpdate(SPREADSHEET_ID, batchRequest)
                .execute();
    }
    private Color getWarningBackgroundColor(InventoryReportRow item) {
        if (item.getQuantityStock() <= 0) {
            return new Color()
                    .setRed(0.9f)
                    .setGreen(0.2f)
                    .setBlue(0.2f);
        }
        if (item.getQuantityStock() <= 5) {
            return new Color()
                    .setRed(1f)
                    .setGreen(0.85f)
                    .setBlue(0.2f);
        }
        return new Color()
                .setRed(0.2f)
                .setGreen(0.65f)
                .setBlue(0.35f);
    }
    private Color getWarningTextColor(InventoryReportRow item) {
        if (item.getQuantityStock() > 0 && item.getQuantityStock() <= 5) {
            return new Color()
                    .setRed(0f)
                    .setGreen(0f)
                    .setBlue(0f);
        }
        return new Color()
                .setRed(1f)
                .setGreen(1f)
                .setBlue(1f);
    }
    private Integer getSheetIdByName(Sheets sheetsService, String sheetName) throws IOException {
        Spreadsheet spreadsheet = sheetsService.spreadsheets()
                .get(SPREADSHEET_ID)
                .execute();
        for (Sheet sheet : spreadsheet.getSheets()) {
            if (sheetName.equals(sheet.getProperties().getTitle())) {
                return sheet.getProperties().getSheetId();
            }
        }
        return null;
    }
}
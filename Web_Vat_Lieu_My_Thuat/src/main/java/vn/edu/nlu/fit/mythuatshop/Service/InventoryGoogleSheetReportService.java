package vn.edu.nlu.fit.mythuatshop.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
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

    private static final String SPREADSHEET_ID = "15voIjtZRvKfAbp9WV8m-aFGtRiDZZ1X_OpINEPoHneo";
    private static final String SPREADSHEET_URL = "https://docs.google.com/spreadsheets/d/15voIjtZRvKfAbp9WV8m-aFGtRiDZZ1X_OpINEPoHneo/edit";
    private static final String CREDENTIALS_PATH = "D:/google-credentials/mythuatshop-sheet-key.json";

    private static final String TEMPLATE_SHEET = "Template";

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
                .setValueInputOption("USER_ENTERED")
                .execute();
    }

    private void clearTodaySheet(Sheets sheetsService, String sheetName) throws IOException {
        ClearValuesRequest clearRequest = new ClearValuesRequest();

        sheetsService.spreadsheets().values()
                .clear(SPREADSHEET_ID, "'" + sheetName + "'!A1:R5000", clearRequest)
                .execute();
    }

    private void writeReportData(Sheets sheetsService, String sheetName, List<InventoryReportRow> rows)
            throws IOException {
        List<List<Object>> values = createReportLayout(rows, false);

        ValueRange body = new ValueRange().setValues(values);

        sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "'" + sheetName + "'!A1", body)
                .setValueInputOption("USER_ENTERED")
                .execute();
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
                "Đã bán hiện tại",
                "Tổng nhập kho",
                "Tổng bán lịch sử",
                "Hoàn kho do hủy đơn",
                "Điều chỉnh kho",
                "Trạng thái kho",
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
                    safe(item.getProductCode()),
                    safe(item.getProductName()),
                    safe(item.getCategoryName()),
                    safe(item.getBrand()),
                    item.getPrice(),
                    item.getDiscountDefault(),
                    item.getPriceAfterDiscount(),
                    item.getQuantityStock(),
                    item.getSoldQuantity(),
                    item.getTotalImported(),
                    item.getTotalSale(),
                    item.getTotalCancel(),
                    item.getTotalAdjust(),
                    item.getStockStatus(),
                    item.getActiveStatus(),
                    item.getStockValue(),
                    item.getLastTransactionAt() == null ? "" : item.getLastTransactionAt().toString()
            ));
        }

        return values;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
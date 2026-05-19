package vn.edu.nlu.fit.mythuatshop.Model.Excel;

import java.util.ArrayList;
import java.util.List;

public class ProductExcelImportResult {
    private final List<String> errors = new ArrayList<>();

    private int processedCount;     // Số sản phẩm đọc từ file Excel
    private int insertedCount;
    private int updatedCount;
    private int unchangedCount;
    private int stockChangedCount;  // Số sản phẩm có thay đổi tồn kho

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
    }

    // Giữ lại hàm cũ để code cũ không lỗi
    public int getImportedCount() {
        return processedCount;
    }

    public void setImportedCount(int importedCount) {
        this.processedCount = importedCount;
    }

    public void increaseImportedCount() {
        this.processedCount++;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(int processedCount) {
        this.processedCount = processedCount;
    }

    public int getInsertedCount() {
        return insertedCount;
    }

    public void setInsertedCount(int insertedCount) {
        this.insertedCount = insertedCount;
    }

    public int getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(int updatedCount) {
        this.updatedCount = updatedCount;
    }

    public int getUnchangedCount() {
        return unchangedCount;
    }

    public void setUnchangedCount(int unchangedCount) {
        this.unchangedCount = unchangedCount;
    }

    public int getStockChangedCount() {
        return stockChangedCount;
    }

    public void setStockChangedCount(int stockChangedCount) {
        this.stockChangedCount = stockChangedCount;
    }
}
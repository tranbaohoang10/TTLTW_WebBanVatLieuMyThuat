package vn.edu.nlu.fit.mythuatshop.Model.Excel;

import java.util.ArrayList;
import java.util.List;

public class ProductExcelImportResult {
    private final List<String> errors = new ArrayList<>();
    private int importedCount;

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getImportedCount() {
        return importedCount;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void increaseImportedCount() {
        importedCount++;
    }

    public void setImportedCount(int importedCount) {
        this.importedCount = importedCount;
    }
}
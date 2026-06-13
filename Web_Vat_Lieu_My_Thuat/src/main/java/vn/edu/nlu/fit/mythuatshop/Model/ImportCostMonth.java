package vn.edu.nlu.fit.mythuatshop.Model;

import java.math.BigDecimal;

public class ImportCostMonth {
    private int month;
    private BigDecimal importCost;

    public ImportCostMonth() {
    }

    public ImportCostMonth(int month, BigDecimal importCost) {
        this.month = month;
        this.importCost = importCost;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getImportCost() {
        return importCost;
    }

    public void setImportCost(BigDecimal importCost) {
        this.importCost = importCost;
    }
}
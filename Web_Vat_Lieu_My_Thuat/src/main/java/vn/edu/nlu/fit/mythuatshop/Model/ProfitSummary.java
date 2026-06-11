package vn.edu.nlu.fit.mythuatshop.Model;

import java.math.BigDecimal;

public class ProfitSummary {
    private BigDecimal revenue;
    private BigDecimal costOfGoodsSold;
    private BigDecimal profit;
    private BigDecimal profitMargin;

    public ProfitSummary() {
        this.revenue = BigDecimal.ZERO;
        this.costOfGoodsSold = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
        this.profitMargin = BigDecimal.ZERO;
    }

    public ProfitSummary(BigDecimal revenue, BigDecimal costOfGoodsSold) {
        this.revenue = revenue != null ? revenue : BigDecimal.ZERO;
        this.costOfGoodsSold = costOfGoodsSold != null ? costOfGoodsSold : BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
        this.profitMargin = BigDecimal.ZERO;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getCostOfGoodsSold() {
        return costOfGoodsSold;
    }

    public void setCostOfGoodsSold(BigDecimal costOfGoodsSold) {
        this.costOfGoodsSold = costOfGoodsSold;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }
}
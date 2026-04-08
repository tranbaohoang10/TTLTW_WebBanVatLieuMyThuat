package vn.edu.nlu.fit.mythuatshop.Model;

import java.util.List;

public class GhnTrackingInfo {
    private String orderCode;
    private String status;
    private String toName;
    private String toPhone;
    private String toAddress;
    private int totalFee;
    private String leadtime;
    private List<GhnTrackingLog> logs;

    public GhnTrackingInfo() {
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(String leadtime) {
        this.leadtime = leadtime;
    }

    public List<GhnTrackingLog> getLogs() {
        return logs;
    }

    public void setLogs(List<GhnTrackingLog> logs) {
        this.logs = logs;
    }
}
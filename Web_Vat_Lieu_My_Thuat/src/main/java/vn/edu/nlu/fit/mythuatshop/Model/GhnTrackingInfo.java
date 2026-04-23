package vn.edu.nlu.fit.mythuatshop.Model;

import java.util.ArrayList;
import java.util.List;

public class GhnTrackingInfo {
    private String orderCode;
    private String clientOrderCode;
    private String status;
    private String leadtime;
    private String toName;
    private String toPhone;
    private String toAddress;
    private String fromName;
    private String fromPhone;
    private String fromAddress;
    private String note;
    private List<GhnTrackingLog> logs = new ArrayList<>();

    public GhnTrackingInfo() {
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getClientOrderCode() {
        return clientOrderCode;
    }

    public void setClientOrderCode(String clientOrderCode) {
        this.clientOrderCode = clientOrderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(String leadtime) {
        this.leadtime = leadtime;
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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<GhnTrackingLog> getLogs() {
        return logs;
    }

    public void setLogs(List<GhnTrackingLog> logs) {
        this.logs = logs;
    }
}
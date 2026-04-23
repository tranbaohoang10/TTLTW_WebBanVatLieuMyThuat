package vn.edu.nlu.fit.mythuatshop.Test;



import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingInfo;
import vn.edu.nlu.fit.mythuatshop.Model.GhnTrackingLog;
import vn.edu.nlu.fit.mythuatshop.Service.GhnService;

public class TestGhnTrackingDetail {
    public static void main(String[] args) {
        try {
            GhnService ghnService = new GhnService();

            String orderCode = "LHANER";
            GhnTrackingInfo info = ghnService.getTrackingDetail(orderCode);

            System.out.println(" CHI TIET VAN DON");
            System.out.println("Order code: " + info.getOrderCode());
            System.out.println("Client order code: " + info.getClientOrderCode());
            System.out.println("Status: " + info.getStatus());
            System.out.println("Leadtime: " + info.getLeadtime());
            System.out.println("To name: " + info.getToName());
            System.out.println("To phone: " + info.getToPhone());
            System.out.println("To address: " + info.getToAddress());

            System.out.println("=== LOG ===");
            System.out.println("So log: " + info.getLogs().size());
            for (GhnTrackingLog log : info.getLogs()) {
                System.out.println("Status: " + log.getStatus() + " - Updated: " + log.getUpdatedDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

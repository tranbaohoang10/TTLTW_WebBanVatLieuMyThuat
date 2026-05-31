package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ProductRecommendationDao;
import vn.edu.nlu.fit.mythuatshop.Model.ProductRecommendation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProductRecommendationService {
    private final ProductRecommendationDao recommendationDao;

    public ProductRecommendationService() {
        this.recommendationDao = new ProductRecommendationDao();
    }

    public List<ProductRecommendation> readCsvFile(String filePath) {
        List<ProductRecommendation> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int userId = Integer.parseInt(parts[0].trim());
                int productId = Integer.parseInt(parts[1].trim());
                double predictedScore = Double.parseDouble(parts[2].trim());

                list.add(new ProductRecommendation(userId, productId, predictedScore));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public int importFromCsv(String filePath) {
        List<ProductRecommendation> list = readCsvFile(filePath);

        if (list.isEmpty()) {
            return 0;
        }

        recommendationDao.deleteAll();
        recommendationDao.insertBatch(list);

        return list.size();
    }
}
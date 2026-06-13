package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ProductRecommendationDao;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;
import vn.edu.nlu.fit.mythuatshop.Model.ProductRecommendation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductRecommendationService {
    private final ProductRecommendationDao recommendationDao;

    public ProductRecommendationService() {
        this.recommendationDao = new ProductRecommendationDao();
    }

    public List<ProductRecommendation> readCsvFile(String filePath) {
        List<ProductRecommendation> list = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null || header.trim().isEmpty()) {
                throw new IllegalArgumentException("File CSV đang rỗng.");
            }
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Dòng " + lineNumber + " phải có đủ 3 cột.");
                }
                try {
                    int userId = Integer.parseInt(parts[0].trim());
                    int productId = Integer.parseInt(parts[1].trim());
                    double predictedScore = Double.parseDouble(parts[2].trim());

                    if (userId <= 0 || productId <= 0) {
                        throw new IllegalArgumentException("Dòng " + lineNumber + " có userID hoặc productID không hợp lệ.");
                    }

                    list.add(new ProductRecommendation(userId, productId, predictedScore));
                }catch (NumberFormatException e) {
                    throw new IllegalArgumentException( "Dòng " + lineNumber + " chứa dữ liệu không đúng kiểu số." );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException( "Không thể đọc file kết quả gợi ý.", e );
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException( "File CSV không có dữ liệu gợi ý." );
        }
        return list;
    }

    public int importFromCsv(String filePath) {
        List<ProductRecommendation> list = readCsvFile(filePath);
        recommendationDao.deleteAll();
        recommendationDao.insertBatch(list);
        return list.size();
    }

    public List<ProductCard> getRecommendProducts(int userId) {
        List<ProductCard> products = recommendationDao.getRecommendedProducts(userId, 5);

        if (products.isEmpty()) {
            products = recommendationDao.getDefaultProducts(5);
        }

        return products;
    }

    public List<ProductCard> getDefaultRecommendProducts() {
        return recommendationDao.getDefaultProducts(5);
    }

    public int countRecommendations() {
        return recommendationDao.countRecommendations();
    }

    public String getLastUpdatedTime() {
        return recommendationDao.getLastUpdatedTime();
    }
}
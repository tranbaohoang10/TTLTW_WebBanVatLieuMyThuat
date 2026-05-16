package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.ProductCardDao;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductCardService {
    private final ProductCardDao dao = new ProductCardDao();

    public List<ProductCard> topByCategory(int categoryId, int limit) {
        return dao.topByCategory(categoryId, limit);
    }

    public List<ProductCard> search(String keyword, String sort, int offset, int limit) {
        keyword = normalizeKeyword(keyword);
        if (sort == null || sort.trim().isEmpty()) {
            sort = "";
        }
        return dao.search(keyword, sort, offset, limit);
    }

    public int countSearch(String keyword) {
        keyword = normalizeKeyword(keyword);
        return dao.countSearch(keyword);
    }
    // ProductCardService.java
    public List<ProductCard> byCategoryWithFilter(int categoryId, Double minPrice, Double maxPrice,
                                                  String sort, int offset, int limit) {
        return dao.byCategoryWithFilter(categoryId, minPrice, maxPrice, sort, offset, limit);
    }

    public int countByCategoryWithFilter(int categoryId, Double minPrice, Double maxPrice) {
        return dao.countByCategoryWithFilter(categoryId, minPrice, maxPrice);
    }
    private String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return "";
        }
        return keyword.trim().replaceAll("\\s+", " ");
    }

    private List<String> splitKeywords(String keyword) {
        keyword = normalizeKeyword(keyword);

        if (keyword.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(keyword.split(" "));
    }
}

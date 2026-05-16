package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.ProductCard;

import java.util.ArrayList;
import java.util.List;

public class ProductCardDao {
    private final Jdbi jdbi;

    public ProductCardDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public List<ProductCard> topByCategory(int categoryId, int limit) {
        String sql = """
            SELECT 
                p.id, p.name, p.price, p.discountDefault,
                p.categoryID AS categoryId,
                p.thumbnail, p.quantityStock, p.soldQuantity,
                p.status, p.createAt, p.brand,
                COALESCE(AVG(pr.rating), 0) AS avgRating,
                COUNT(pr.id) AS reviewCount
            FROM products p
            LEFT JOIN product_reviews pr ON pr.productID = p.id
            WHERE p.categoryID = :categoryId
              AND p.isActive = 1
            GROUP BY 
                p.id, p.name, p.price, p.discountDefault, p.categoryID,
                p.thumbnail, p.quantityStock, p.soldQuantity, p.status, p.createAt, p.brand
            ORDER BY p.soldQuantity DESC
            LIMIT :limit
        """;

        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .bind("limit", limit)
                        .mapToBean(ProductCard.class)
                        .list()
        );
    }

    public List<ProductCard> search(String keyword, String sort, int offset, int limit) {
        String orderBy;
        switch (sort) {
            case "priceAsc" -> orderBy = " ORDER BY (p.price * (100.0 - p.discountDefault)/100.0) ASC ";
            case "priceDesc"-> orderBy = " ORDER BY (p.price * (100.0 - p.discountDefault)/100.0) DESC ";
            case "newest"   -> orderBy = " ORDER BY p.createAt DESC ";
            case "soldDesc" -> orderBy = " ORDER BY p.soldQuantity DESC ";
            default         -> orderBy = " ORDER BY p.soldQuantity DESC ";
        }

        List<String> keywords = splitKeywords(keyword);

        StringBuilder sql = new StringBuilder("""
        SELECT 
            p.id, p.name, p.price, p.discountDefault,
            p.categoryID AS categoryId,
            p.thumbnail, p.quantityStock, p.soldQuantity,
            p.status, p.createAt, p.brand,
            COALESCE(AVG(pr.rating), 0) AS avgRating,
            COUNT(pr.id) AS reviewCount
        FROM products p
        LEFT JOIN product_reviews pr ON pr.productID = p.id
        WHERE p.isActive = 1
    """);

        for (int i = 0; i < keywords.size(); i++) {
            sql.append(" AND p.name LIKE CONCAT('%', :kw").append(i).append(", '%') ");
        }

        sql.append("""
        GROUP BY 
            p.id, p.name, p.price, p.discountDefault, p.categoryID,
            p.thumbnail, p.quantityStock, p.soldQuantity, p.status, p.createAt, p.brand
    """);

        sql.append(orderBy);
        sql.append(" LIMIT :limit OFFSET :offset ");

        return jdbi.withHandle(h -> {
            var query = h.createQuery(sql.toString())
                    .bind("limit", limit)
                    .bind("offset", offset);

            for (int i = 0; i < keywords.size(); i++) {
                query.bind("kw" + i, keywords.get(i));
            }

            return query.mapToBean(ProductCard.class).list();
        });
    }

    public int countSearch(String keyword) {
        List<String> keywords = splitKeywords(keyword);

        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
        FROM products p
        WHERE p.isActive = 1
    """);

        for (int i = 0; i < keywords.size(); i++) {
            sql.append(" AND p.name LIKE CONCAT('%', :kw").append(i).append(", '%') ");
        }

        return jdbi.withHandle(h -> {
            var query = h.createQuery(sql.toString());

            for (int i = 0; i < keywords.size(); i++) {
                query.bind("kw" + i, keywords.get(i));
            }

            return query.mapTo(Integer.class).one();
        });
    }



    public List<ProductCard> byCategoryWithFilter(int categoryId,
                                                  Double minPrice,
                                                  Double maxPrice,
                                                  String sort,
                                                  int offset,
                                                  int limit) {
        sort = (sort == null) ? "" : sort;
        String orderBy = switch (sort) {
            case "priceAsc"  -> " ORDER BY (p.price * (100.0 - p.discountDefault)/100.0) ASC ";
            case "priceDesc" -> " ORDER BY (p.price * (100.0 - p.discountDefault)/100.0) DESC ";
            case "newest"    -> " ORDER BY p.createAt DESC ";
            case "soldDesc"  -> " ORDER BY p.soldQuantity DESC ";
            default          -> " ORDER BY p.soldQuantity DESC ";
        };

        StringBuilder sql = new StringBuilder("""
            SELECT
                p.id, p.name, p.price, p.discountDefault,
                p.categoryID AS categoryId,
                p.thumbnail, p.quantityStock, p.soldQuantity,
                p.status, p.createAt, p.brand,
                COALESCE(AVG(pr.rating), 0) AS avgRating,
                COUNT(pr.id) AS reviewCount
            FROM products p
            LEFT JOIN product_reviews pr ON pr.productID = p.id
            WHERE p.categoryID = :categoryId
              AND p.isActive = 1
        """);

        if (minPrice != null) {
            sql.append(" AND (p.price * (100.0 - p.discountDefault)/100.0) >= :minPrice ");
        }
        if (maxPrice != null) {
            sql.append(" AND (p.price * (100.0 - p.discountDefault)/100.0) <= :maxPrice ");
        }

        sql.append("""
            GROUP BY
                p.id, p.name, p.price, p.discountDefault, p.categoryID,
                p.thumbnail, p.quantityStock, p.soldQuantity, p.status, p.createAt, p.brand
        """);

        sql.append(orderBy);
        sql.append(" LIMIT :limit OFFSET :offset ");

        return jdbi.withHandle(h -> {
            var q = h.createQuery(sql.toString())
                    .bind("categoryId", categoryId)
                    .bind("limit", limit)
                    .bind("offset", offset);

            if (minPrice != null) q.bind("minPrice", minPrice);
            if (maxPrice != null) q.bind("maxPrice", maxPrice);

            return q.mapToBean(ProductCard.class).list();
        });
    }

    public int countByCategoryWithFilter(int categoryId, Double minPrice, Double maxPrice) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM products p
            WHERE p.categoryID = :categoryId
              AND p.isActive = 1
        """);

        if (minPrice != null) {
            sql.append(" AND (p.price * (100.0 - p.discountDefault)/100.0) >= :minPrice ");
        }
        if (maxPrice != null) {
            sql.append(" AND (p.price * (100.0 - p.discountDefault)/100.0) <= :maxPrice ");
        }

        return jdbi.withHandle(h -> {
            var q = h.createQuery(sql.toString()).bind("categoryId", categoryId);
            if (minPrice != null) q.bind("minPrice", minPrice);
            if (maxPrice != null) q.bind("maxPrice", maxPrice);
            return q.mapTo(Integer.class).one();
        });
    }
    private List<String> splitKeywords(String keyword) {
        List<String> keywords = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return keywords;
        }

        String[] parts = keyword.trim().replaceAll("\\s+", " ").split(" ");

        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                keywords.add(part.trim());
            }
        }

        return keywords;
    }
}

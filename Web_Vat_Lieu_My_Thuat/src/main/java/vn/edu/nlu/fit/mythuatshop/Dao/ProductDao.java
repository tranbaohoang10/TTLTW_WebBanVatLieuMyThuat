package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Product;

import java.util.List;

public class ProductDao {
    private final Jdbi jdbi;

    public ProductDao() {
        jdbi = JDBIConnector.getJdbi();
    }



    public List<Product> findAll() {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products";
        return jdbi.withHandle(h -> h.createQuery(sql).mapToBean(Product.class).list());
    }

    // Admin cần lấy theo id (để update vẫn lấy được thumbnail cũ)
    public Product findByProductId(int productId) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE id = :productId";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("productId", productId)
                        .mapToBean(Product.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Client chỉ lấy sản phẩm đang active

    public Product findByProductIdActive(int productId) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE id = :productId AND isActive = 1";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("productId", productId)
                        .mapToBean(Product.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Product> findByCategoryId(int categoryId) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE categoryId = :categoryId AND isActive = 1 " +
                "ORDER BY soldQuantity DESC " +
                "LIMIT 10";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> findByCategoryIdTop5(int categoryId) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE categoryId = :categoryId AND isActive = 1 " +
                "ORDER BY soldQuantity DESC " +
                "LIMIT 6";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> findByCategoryIdNoLimit(int categoryId) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE categoryId = :categoryId AND isActive = 1 " +
                "ORDER BY soldQuantity DESC";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    // ===================== FILTER (client) =====================

    public List<Product> findByCategoryWithFilter(int categoryId,
                                                  Double minPrice,
                                                  Double maxPrice,
                                                  String sort) {

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM Products WHERE categoryId = :categoryId AND isActive = 1 ");

        if (minPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 >= :minPrice");
        }
        if (maxPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 <= :maxPrice");
        }

        if ("priceAsc".equals(sort)) {
            sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 ASC");
        } else if ("priceDesc".equals(sort)) {
            sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 DESC");
        } else if ("newest".equals(sort)) {
            sql.append(" ORDER BY createAt DESC");
        } else {
            sql.append(" ORDER BY createAt DESC");
        }

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql.toString())
                    .bind("categoryId", categoryId);

            if (minPrice != null) query.bind("minPrice", minPrice);
            if (maxPrice != null) query.bind("maxPrice", maxPrice);

            return query.mapToBean(Product.class).list();
        });
    }

    public List<Product> findByCategoryWithFilter(int categoryId,
                                                  Double minPrice,
                                                  Double maxPrice,
                                                  String sort,
                                                  int offset,
                                                  int limit) {

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM Products WHERE categoryId = :categoryId AND isActive = 1 ");

        if (minPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 >= :minPrice");
        }
        if (maxPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 <= :maxPrice");
        }

        if ("priceAsc".equals(sort)) {
            sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 ASC");
        } else if ("priceDesc".equals(sort)) {
            sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 DESC");
        } else if ("newest".equals(sort)) {
            sql.append(" ORDER BY createAt DESC");
        } else {
            sql.append(" ORDER BY createAt DESC");
        }

        sql.append(" LIMIT :limit OFFSET :offset");

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql.toString())
                    .bind("categoryId", categoryId)
                    .bind("limit", limit)
                    .bind("offset", offset);

            if (minPrice != null) query.bind("minPrice", minPrice);
            if (maxPrice != null) query.bind("maxPrice", maxPrice);

            return query.mapToBean(Product.class).list();
        });
    }

    public int countProductsByCategory(int categoryId, Double minPrice, Double maxPrice) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM Products WHERE categoryId = :categoryId AND isActive = 1 ");

        if (minPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 >= :minPrice");
        }
        if (maxPrice != null) {
            sql.append(" AND price * (100.0 - discountDefault) / 100.0 <= :maxPrice");
        }

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql.toString())
                    .bind("categoryId", categoryId);

            if (minPrice != null) query.bind("minPrice", minPrice);
            if (maxPrice != null) query.bind("maxPrice", maxPrice);

            return query.mapTo(Integer.class).one();
        });
    }

    // ===================== SEARCH (client) =====================

    public List<Product> getProductSearch(String productName) {
        String sql = "SELECT id, name, price, discountDefault, categoryId, " +
                "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                "FROM Products " +
                "WHERE isActive = 1 AND name LIKE CONCAT('%', :productName, '%')";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("productName", productName)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public List<Product> getProductSearch(String productName, String sort, int offset, int limit) {
        StringBuilder sql = new StringBuilder(
                "SELECT id, name, price, discountDefault, categoryId, " +
                        "thumbnail, quantityStock, soldQuantity, status, createAt, brand, isActive " +
                        "FROM Products " +
                        "WHERE isActive = 1 AND name LIKE CONCAT('%', :productName, '%') ");

        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "priceAsc":
                    sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 ASC");
                    break;
                case "priceDesc":
                    sql.append(" ORDER BY price * (100.0 - discountDefault) / 100.0 DESC");
                    break;
                case "newest":
                    sql.append(" ORDER BY createAt DESC");
                    break;
                case "soldDesc":
                    sql.append(" ORDER BY soldQuantity DESC");
                    break;
                default:
                    sql.append(" ORDER BY soldQuantity DESC");
                    break;
            }
        }

        sql.append(" LIMIT :limit OFFSET :offset");

        return jdbi.withHandle(h ->
                h.createQuery(sql.toString())
                        .bind("productName", productName)
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(Product.class)
                        .list()
        );
    }

    public int countProductSearch(String productName) {
        String sql = "SELECT COUNT(*) FROM Products " +
                "WHERE isActive = 1 AND name LIKE CONCAT('%', :productName, '%')";
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("productName", productName)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int updateStockAndSold(Handle handle, int productId, int qty) {
        String sql = """
                UPDATE Products
                SET quantityStock = quantityStock - :qty,
                    soldQuantity = soldQuantity + :qty
                WHERE id = :pid
                  AND isActive = 1
                  AND quantityStock >= :qty
                """;
        return handle.createUpdate(sql)
                .bind("pid", productId)
                .bind("qty", qty)
                .execute();
    }

    public int restoreStockAndSold(Handle handle, int productId, int qty) {
        String sql = """
                    UPDATE Products
                    SET quantityStock = quantityStock + :qty,
                        soldQuantity = CASE
                            WHEN soldQuantity >= :qty THEN soldQuantity - :qty
                            ELSE 0
                        END
                    WHERE id = :pid
                """;
        return handle.createUpdate(sql)
                .bind("pid", productId)
                .bind("qty", qty)
                .execute();
    }

    // ===================== CRUD ADMIN =====================

    public int insertReturnId(Product p) {
        String sql = """
                INSERT INTO Products(name, price, discountDefault, categoryID, thumbnail,
                                     quantityStock, soldQuantity, status, createAt, brand, isActive)
                VALUES (:name, :price, :discountDefault, :categoryID, :thumbnail,
                        :quantityStock, :soldQuantity, :status, :createAt, :brand, :isActive)
                """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("name", p.getName())
                        .bind("price", p.getPrice())
                        .bind("discountDefault", p.getDiscountDefault())
                        .bind("categoryID", p.getCategoryId())
                        .bind("thumbnail", p.getThumbnail())
                        .bind("quantityStock", p.getQuantityStock())
                        .bind("soldQuantity", p.getSoldQuantity())
                        .bind("status", p.getStatus())
                        .bind("createAt", p.getCreateAt())
                        .bind("brand", p.getBrand())
                        .bind("isActive", p.getIsActive())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int update(Product p) {
        String sql = """
                UPDATE Products
                SET name = :name,
                    price = :price,
                    discountDefault = :discountDefault,
                    categoryID = :categoryID,
                    thumbnail = :thumbnail,
                    quantityStock = :quantityStock,
                    brand = :brand
                WHERE id = :id
                """;

        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", p.getId())
                        .bind("name", p.getName())
                        .bind("price", p.getPrice())
                        .bind("discountDefault", p.getDiscountDefault())
                        .bind("categoryID", p.getCategoryId())
                        .bind("thumbnail", p.getThumbnail())
                        .bind("quantityStock", p.getQuantityStock())
                        .bind("brand", p.getBrand())
                        .execute()
        );
    }

    public int updateActive(int id, int isActive) {
        String sql = "UPDATE Products SET isActive = :isActive WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", id)
                        .bind("isActive", isActive)
                        .execute()
        );
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM Products WHERE id = :id";
        return jdbi.withHandle(h -> h.createUpdate(sql).bind("id", id).execute());
    }
}

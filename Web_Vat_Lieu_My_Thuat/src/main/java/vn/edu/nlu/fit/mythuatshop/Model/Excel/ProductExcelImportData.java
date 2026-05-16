package vn.edu.nlu.fit.mythuatshop.Model.Excel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductExcelImportData {
    private Map<String, ProductExcelRow> products = new LinkedHashMap<>();
    private List<SubImageExcelRow> subImages = new ArrayList<>();
    private Map<String, SpecificationExcelRow> specifications = new LinkedHashMap<>();

    public Map<String, ProductExcelRow> getProducts() {
        return products;
    }

    public void setProducts(Map<String, ProductExcelRow> products) {
        this.products = products;
    }

    public List<SubImageExcelRow> getSubImages() {
        return subImages;
    }

    public void setSubImages(List<SubImageExcelRow> subImages) {
        this.subImages = subImages;
    }

    public Map<String, SpecificationExcelRow> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Map<String, SpecificationExcelRow> specifications) {
        this.specifications = specifications;
    }
}
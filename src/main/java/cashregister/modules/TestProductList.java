package cashregister.modules;

import java.util.Map;
import java.util.TreeMap;

public class TestProductList {

    public TestProductList() {
        products = new TreeMap<>();
        products.put(11, "Masło extra");
        products.put(22, "Żubrówka biała");
        products.put(33, "Chleb żytni");
        products.put(44, "Czekolada gorzka");
        products.put(55, "Chusteczki higeniczne");
    }

    public String getProduct(int barcode) {
        return products.get(barcode);
    }

    public Map<Integer, String> products;
}
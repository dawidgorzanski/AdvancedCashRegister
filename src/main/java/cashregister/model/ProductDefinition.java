package cashregister.model;

import java.util.ArrayList;
import java.util.List;

public class ProductDefinition {
    private int id;
    private String name;
    private double quantity;
    private double price;
    private boolean countable;
    private String barcode;
    private List<ProductForSale> productForSales;

    public ProductDefinition() {
        this.id = 0;
    }

    public ProductDefinition(int id, String name, double quantity, double price, String barcode, boolean countable) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.countable = countable;
        this.barcode = barcode;
        this.productForSales = new ArrayList<ProductForSale>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return new String(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    public String getBarcode() {
        return new String(barcode);
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<ProductForSale> getProductForSales() {
        return productForSales;
    }

    public void setProductForSales(List<ProductForSale> productForSales) {
        this.productForSales = productForSales;
    }
}

package cashregister.model;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class ProductDefinition {
    private int id;
    private String name;
    private double quantity;
    private double price;
    private boolean countable;
    private String barcode;
    private boolean ageLimit;
    private List<ProductForSale> productForSales;

    public ProductDefinition() {
        this.id = 0;
    }

    public ProductDefinition(int id, String name, double quantity, double price, String barcode, boolean countable, boolean ageLimit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.countable = countable;
        this.barcode = barcode;
        this.ageLimit = ageLimit;
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

    public void decreaseQuantityBy(double quantity) {
        this.quantity -= quantity;
        if(this.quantity<0)
        {
            showNegativeQuantityAlert();
        }
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

    public boolean getAgeLimit() { return ageLimit;}

    public void setAgeLimit(boolean ageLimit) { this.ageLimit = ageLimit;}

    public List<ProductForSale> getProductForSales() {
        return productForSales;
    }

    public void setProductForSales(List<ProductForSale> productForSales) {
        this.productForSales = productForSales;
    }

    private void showNegativeQuantityAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ilość produktów w bazie mniejsza od 0");
        alert.setHeaderText("Ilość produktów w bazie mniejsza od 0");
        alert.setContentText("Proszę powiadomić administratora.");
        alert.showAndWait();
    }
}

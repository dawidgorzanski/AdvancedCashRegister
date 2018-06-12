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

    /**
     * Initializes instance with attributes passed as parameters
     * @param id
     * @param name
     * @param quantity
     * @param price
     * @param barcode
     * @param countable
     * @param ageLimit
     */
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

    /**
     *
     * @return id of ProductDefinition
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of ProductDefinition
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return name of ProductDefinition
     */
    public String getName() {
        return new String(name);
    }

    /**
     * Sets name of ProductDefinition
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return quantity of ProductDefinition
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity of ProductDefinition
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Decreases quantity of ProductDefinition by value passed as parameter
     * @param quantity
     */
    public void decreaseQuantityBy(double quantity) {
        this.quantity -= quantity;
        if(this.quantity<0)
        {
            showNegativeQuantityAlert();
        }
    }

    /**
     *
     * @return price of ProductDefinition
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price of ProductDefinition
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return true if ProductDefinition is countable, false otherwise
     */
    public boolean getCountable() {
        return countable;
    }

    /**
     * Sets whether ProductDefinition is countable or not
     * @param countable
     */
    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    /**
     *
     * @return barcode of ProductDefiniton
     */
    public String getBarcode() {
        return new String(barcode);
    }

    /**
     * Sets barcode of ProductDefinition
     * @param barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     *
     * @return true if ProductDefinition is for adults, false otherwise
     */
    public boolean getAgeLimit() { return ageLimit;}

    /**
     * Sets if ProductDefinition is for adults or not
     * @param ageLimit
     */
    public void setAgeLimit(boolean ageLimit) { this.ageLimit = ageLimit;}

    /**
     *
     * @return List of ProductForSale objects assigned to ProductDefinition
     */
    public List<ProductForSale> getProductForSales() {
        return productForSales;
    }

    /**
     * Assigns list of ProductForSale objects to ProductDefinition
     * @param productForSales
     */
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

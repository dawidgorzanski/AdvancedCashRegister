package cashregister.model;

import javafx.beans.property.*;

public class ProductForSale {
    private int id;
    private StringProperty name;
    private DoubleProperty quantity;
    private DoubleProperty price;
    private DoubleProperty totalPrice;
    private BooleanProperty countable;
    private ProductDefinition productDefinition;
    private Receipt receipt;


    /**
     * Initializes attributes with default values
     */
    public ProductForSale() {
        this.name = new SimpleStringProperty("");
        this.quantity = new SimpleDoubleProperty(0);
        this.price = new SimpleDoubleProperty(0);
        this.totalPrice = new SimpleDoubleProperty(0);
        this.countable = new SimpleBooleanProperty(true);
    }

    /**
     * Initializes attributes with given as parameters
     * @param name
     * @param quantity
     * @param price
     * @param countable tells if product amount is countable
     */
    public ProductForSale(String name, double quantity, double price, boolean countable) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.totalPrice = new SimpleDoubleProperty(quantity * price);
        this.countable = new SimpleBooleanProperty(countable);
    }

    /**
     * Initializes attributes based on ProductDefinition passed as parameter
     * @param productDefinition
     */
    public ProductForSale(ProductDefinition productDefinition) {
        this.name = new SimpleStringProperty(productDefinition.getName());
        this.quantity = new SimpleDoubleProperty(1);
        this.price = new SimpleDoubleProperty(productDefinition.getPrice());
        this.totalPrice = new SimpleDoubleProperty(productDefinition.getPrice());
        this.countable = new SimpleBooleanProperty(productDefinition.getCountable());
        this.productDefinition = productDefinition;
    }

    /**
     * Gets id of ProductForSale
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of ProductForSale
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns name of ProductForSale
     * @return name of ProductForSale
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets name of ProductForSale
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns StringProperty that keeps name
     * @return StringProperty that keeps name
     */
    public StringProperty nameProperty() {
        return this.name;
    }

    /**
     *
     * @return quantity of ProductForSale
     */
    public double getQuantity() {
        return this.quantity.get();
    }

    /**
     * Sets quantity of ProductForSale
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
        this.totalPrice.set(this.getPrice() * this.getQuantity());
    }

    /**
     *
     * @return DoubleProperty that keeps quantity of ProductForSale
     */
    public DoubleProperty quantityProperty() {
        return this.quantity;
    }

    /**
     *
     * @return price of ProductForSale
     */
    public double getPrice() {
        return this.price.get();
    }

    /**
     * Sets price of ProductForSale
     * @param price
     */
    public void setPrice(double price) {
        this.price.set(price);
        this.totalPrice.set(this.getPrice() * this.getQuantity());
    }

    /**
     *
     * @return DoubleProperty that keeps price of ProductForSale
     */
    public DoubleProperty priceProperty() {
        return this.price;
    }

    /**
     *
     * @return total price of ProductForSale
     */
    public double getTotalPrice() {
        return this.totalPrice.get();
    }

    /**
     *
     * @return DoubleProperty that keeps price of ProductForSale
     */
    public DoubleProperty totalPriceProperty() {
        return this.totalPrice;
    }

    /**
     *
     * @return true if Product is countable, false otherwise
     */
    public boolean getCountable() {
        return countable.get();
    }

    /**
     *
     * @return BooleanProperty that says if ProductForSale is countable or not
     */
    public BooleanProperty countableProperty() {
        return countable;
    }

    /**
     * Set whether ProductForSale is countable or not
     * @param countable
     */
    public void setCountable(boolean countable) {
        this.countable.set(countable);
    }

    /**
     *
     * @return ProductDefinition for ProductForSale
     */
    public ProductDefinition getProductDefinition() {
        return productDefinition;
    }

    /**
     * Sets ProductDefinition for ProductForSale
     * @param productDefinition
     */
    public void setProductDefinition(ProductDefinition productDefinition) {
        this.productDefinition = productDefinition;
    }

    /**
     *
     * @return Receipt which ProductForSale belongs to
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Sets Receipt which ProductsForSale will belong to
     * @param receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}

package cashregister.model;

import javafx.beans.property.*;

public class ProductForSale {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty quantity;
    private DoubleProperty price;
    private DoubleProperty totalPrice;
    private BooleanProperty countable;
    private ProductDefinition productDefinition;
//    private static int nextId = 0;
//    public static int nextId(){return ++nextId;}


    public ProductForSale() {
        id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.quantity = new SimpleDoubleProperty(0);
        this.price = new SimpleDoubleProperty(0);
        this.totalPrice = new SimpleDoubleProperty(0);
        this.countable = new SimpleBooleanProperty(true);
    }

    public ProductForSale(int id, String name, double quantity, double price, boolean countable) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.totalPrice = new SimpleDoubleProperty(quantity * price);
        this.countable = new SimpleBooleanProperty(countable);
    }

    public ProductForSale(ProductDefinition productDefinition) {
        id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(productDefinition.getName());
        this.quantity = new SimpleDoubleProperty(1);
        this.price = new SimpleDoubleProperty(productDefinition.getPrice());
        this.totalPrice = new SimpleDoubleProperty(productDefinition.getPrice());
        this.countable = new SimpleBooleanProperty(productDefinition.getCountable());
        this.productDefinition = productDefinition;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public double getQuantity() {
        return this.quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
        this.totalPrice.set(this.getPrice() * this.getQuantity());
    }

    public DoubleProperty quantityProperty() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
        this.totalPrice.set(this.getPrice() * this.getQuantity());
    }

    public DoubleProperty priceProperty() {
        return this.price;
    }

    public double getTotalPrice() {
        return this.totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return this.totalPrice;
    }

    public boolean getCountable() {
        return countable.get();
    }

    public BooleanProperty countableProperty() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable.set(countable);
    }

    public ProductDefinition getProductDefinition() {
        return productDefinition;
    }

    public void setProductDefinition(ProductDefinition productDefinition) {
        this.productDefinition = productDefinition;
    }
}

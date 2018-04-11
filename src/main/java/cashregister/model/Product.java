package cashregister.model;

import javafx.beans.property.*;

public class Product {

    private StringProperty name;
    private DoubleProperty quantity;
    private DoubleProperty price;

    public Product(String name) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(1);
        this.price = new SimpleDoubleProperty(20);
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public final double getQuantity() {
        return this.quantity.get();
    }

    public final void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public DoubleProperty quantityProperty() {
        return this.quantity;
    }

    public final double getPrice() {
        return this.price.get();
    }

    public final void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return this.price;
    }
}

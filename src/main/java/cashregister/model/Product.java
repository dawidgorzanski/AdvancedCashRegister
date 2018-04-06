package cashregister.model;

public class Product {

    public Product(String name) { this.name = name; quantity = 1; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    private String name;
    private  int quantity;
}

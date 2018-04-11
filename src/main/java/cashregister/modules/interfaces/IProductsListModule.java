package cashregister.modules.interfaces;

import cashregister.model.Product;
import javafx.collections.ObservableList;

public interface IProductsListModule {
    void addProduct(int barcode);
    void deleteProduct(Product product);
    void changeQuantity(Product product, int quantity);
    void addDiscount(Product product, float discount);
    public ObservableList getShoppingList();
}

package cashregister.modules.interfaces;

import cashregister.model.ProductForSale;
import javafx.collections.ObservableList;

public interface IProductsListModule {
    void addProduct(String barcode);
    void deleteProduct(ProductForSale product);
    void changeQuantity(ProductForSale product, int quantity);
    void addDiscount(ProductForSale product, float discount);
    public ObservableList getShoppingList();
}

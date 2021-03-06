package cashregister.modules.interfaces;

import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import javafx.collections.ObservableList;
import cashregister.model.ProductDefinition;

public interface IProductsListModule {
    void addProduct(String barcode);
    void deleteProduct(ProductForSale product);
    void deleteAllProducts();
    void changeQuantity(ProductForSale product, int quantity);
    void addDiscount(ProductForSale product, float discount);
    ObservableList getShoppingList();
    void setCustomerForTransaction(Customer customer);
    void deleteCustomerFromTransaction();
    Customer getCurrentCustomer();
    double getTotalPrice();
    ProductDefinition getByBarcode(String barcode);

}

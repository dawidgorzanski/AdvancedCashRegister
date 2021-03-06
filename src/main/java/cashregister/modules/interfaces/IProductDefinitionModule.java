package cashregister.modules.interfaces;

import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import javafx.collections.ObservableList;

public interface IProductDefinitionModule {
    void addProduct(ProductDefinition product);
    void updateProduct(ProductDefinition product);
    void deleteProduct(ProductDefinition product);
    ObservableList<ProductDefinition> getByName(String name);
    ProductDefinition getByBarcode(String barcode);
    ObservableList getAllProducts();
}

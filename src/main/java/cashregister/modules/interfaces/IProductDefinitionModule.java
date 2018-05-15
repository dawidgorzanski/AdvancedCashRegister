package cashregister.modules.interfaces;

import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import javafx.collections.ObservableList;

public interface IProductDefinitionModule {
    void addProduct(ProductDefinition product);
    void deleteProduct(ProductDefinition product);
    ObservableList getAllProducts();
}

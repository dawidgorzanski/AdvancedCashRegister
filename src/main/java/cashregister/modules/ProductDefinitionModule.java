package cashregister.modules;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;

public class ProductDefinitionModule implements IProductDefinitionModule {

    private IProductDefinitionDao productDefinitionDao;

    public ProductDefinitionModule(IProductDefinitionDao productDefinitionDao)
    {
        this.productDefinitionDao = productDefinitionDao;
    }

    public ObservableList<ProductDefinition> getAllProducts()
    {
        List<ProductDefinition> products = productDefinitionDao.getAll();
        ObservableList<ProductDefinition> productsList = FXCollections.observableArrayList(products);
        return productsList;
    }

    public void addProduct(ProductDefinition product)
    {
        productDefinitionDao.saveOrUpdate(product);
    }

    public void deleteProduct(ProductDefinition product)
    {
        productDefinitionDao.delete(product);
    }

    public void changeQuantity(ProductDefinition product, int quantity) { }

}

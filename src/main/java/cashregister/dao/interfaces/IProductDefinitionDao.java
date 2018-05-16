package cashregister.dao.interfaces;

import cashregister.model.ProductDefinition;
import javafx.collections.ObservableList;

public interface IProductDefinitionDao extends IBaseDao<ProductDefinition> {
    ProductDefinition getByBarcode(String barcode);
    ObservableList<ProductDefinition> getByName(String name);
}

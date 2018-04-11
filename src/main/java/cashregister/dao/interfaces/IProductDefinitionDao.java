package cashregister.dao.interfaces;

import cashregister.model.ProductDefinition;

public interface IProductDefinitionDao extends IBaseDao<ProductDefinition> {
    ProductDefinition getByBarcode(String barcode);
}

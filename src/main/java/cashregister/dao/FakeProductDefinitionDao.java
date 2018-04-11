package cashregister.dao;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FakeProductDefinitionDao implements IProductDefinitionDao {
    public List<ProductDefinition> productDefinitions;

    public FakeProductDefinitionDao() {
        productDefinitions = new ArrayList<ProductDefinition>();
        productDefinitions.add(new ProductDefinition(11, "Masło extra", 100, 5.50, "11", true));
        productDefinitions.add(new ProductDefinition(22, "Żubrówka biała", 100, 19.90, "22", true));
        productDefinitions.add(new ProductDefinition(33, "Chleb żytni", 100, 4.50, "33", true));
    }

    @Override
    public ProductDefinition getById(int id) {
        for(ProductDefinition productDefinition : productDefinitions) {
            if (productDefinition.getId() == id)
                return productDefinition;
        }

        return null;
    }

    @Override
    public List<ProductDefinition> getAll() {
        return productDefinitions;
    }

    @Override
    public ProductDefinition save(ProductDefinition object) {
        productDefinitions.add(object);
        return object;
    }

    @Override
    public void saveOrUpdate(ProductDefinition object) {
        productDefinitions.add(object);
    }

    @Override
    public void delete(Object object) {
        productDefinitions.remove(object);
    }

    @Override
    public ProductDefinition getByBarcode(String barcode) {
        for(ProductDefinition productDefinition : productDefinitions) {
            if (productDefinition.getBarcode().equals(barcode))
                return productDefinition;
        }

        return null;
    }
}
package cashregister.modules;

import cashregister.model.enums.ObjectType;
import cashregister.modules.interfaces.IBarcodeChecker;

public class BarcodeChecker implements IBarcodeChecker {
    @Override
    public ObjectType getObjectTypeByBarcode(String barcode) {
        if (barcode.length() == 15){
            return ObjectType.User;
        }
        else {
            return ObjectType.Product;
        }
    }
}

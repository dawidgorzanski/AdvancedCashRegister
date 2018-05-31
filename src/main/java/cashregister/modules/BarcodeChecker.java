package cashregister.modules;

import cashregister.model.enums.ObjectType;
import cashregister.modules.interfaces.IBarcodeChecker;

public class BarcodeChecker implements IBarcodeChecker {
    @Override
    public ObjectType getObjectTypeByBarcode(String barcode) {
        if (barcode.length() == 15){
            return ObjectType.User;
        }
        else if (barcode.length() == 13){
            return ObjectType.Product;
        }
        else
            return ObjectType.Unknown;
    }
}

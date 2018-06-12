package cashregister.modules;

import cashregister.model.enums.ObjectType;
import cashregister.modules.interfaces.IBarcodeChecker;


/**
 * determines ObjectType of object associated with barcode
 */
public class BarcodeChecker implements IBarcodeChecker {
    /**
     * Returns ObjectType of object associated with barcode
     * @param barcode barcode that determines object
     * @return desirable ObjectType i.e ObjectType.User, ObjectType.Product, or ObjectType.Unknown
     */
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

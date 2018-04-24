package cashregister.modules.interfaces;

import cashregister.model.enums.ObjectType;

public interface IBarcodeChecker {
    ObjectType getObjectTypeByBarcode(String barcode);
}

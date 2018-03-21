package cashregister.barcode;

import java.util.EventListener;

public interface IBarcodeReaderDataListener extends EventListener {
    void barcodeValueArrived(String value);
}

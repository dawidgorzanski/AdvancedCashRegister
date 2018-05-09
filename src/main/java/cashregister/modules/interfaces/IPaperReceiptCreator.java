package cashregister.modules.interfaces;

import cashregister.model.Receipt;

public interface IPaperReceiptCreator {
    String createPaperReceipt(Receipt receipt);
}

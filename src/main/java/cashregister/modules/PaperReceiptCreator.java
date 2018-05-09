package cashregister.modules;

import cashregister.modules.interfaces.IPaperReceiptCreator;
import cashregister.model.Receipt;
import cashregister.model.ProductForSale;

public class PaperReceiptCreator implements IPaperReceiptCreator {
    public String createPaperReceipt(Receipt receipt) {
        StringBuilder builder = new StringBuilder();
        float sum = 0;
        builder.append("Paragon Fiskalny\n");
        for(ProductForSale ps : receipt.getProductForSales()) {
            builder.append(ps.getName() + "\t" + ps.getQuantity() + "*" + ps.getPrice() + "\t"
                    + ps.getTotalPrice() + "\n");

            sum += ps.getTotalPrice();
        }

        builder.append("\n\nRazem: " + sum + "zł");

        return builder.toString();
    }
}

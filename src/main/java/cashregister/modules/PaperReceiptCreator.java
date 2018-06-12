package cashregister.modules;

import cashregister.modules.interfaces.IPaperReceiptCreator;
import cashregister.model.Receipt;
import cashregister.model.ProductForSale;

/**
 * Responsible for creating paper receipts
 */
public class PaperReceiptCreator implements IPaperReceiptCreator {
    /**
     * Creates new paper receipt(String) based on passed Receipt
     * @param receipt receipt object
     * @return
     */
    public String createPaperReceipt(Receipt receipt) {
        StringBuilder builder = new StringBuilder();
        float sum = 0;
        builder.append("Paragon Fiskalny\n");
        for(ProductForSale ps : receipt.getProductForSales()) {
            String totalPrice = "" + ps.getTotalPrice();
            if(totalPrice.length() > 5)
                totalPrice = totalPrice.substring(0,5);
            builder.append(ps.getName() + "\t" + ps.getQuantity() + "*" + ps.getPrice() + "\t"
                    + totalPrice + "\n");

            sum += ps.getTotalPrice();
        }

        builder.append("\n\nRazem: " + sum + "zł");

        return builder.toString();
    }
}

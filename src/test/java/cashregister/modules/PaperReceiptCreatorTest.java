package cashregister.modules;

import cashregister.model.ProductForSale;
import cashregister.model.Receipt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PaperReceiptCreatorTest {
    @Mock
    private Receipt receiptMock;

    @Test
    public void createPaperReceipt_FineValues_ReturnPaperReceiptProperly() {
        List<ProductForSale> psList = new ArrayList<>();
        psList.add(new ProductForSale("beer", 20, 2.39, true));
        Mockito.when((receiptMock).getProductForSales()).thenReturn(psList);

        PaperReceiptCreator paperReceiptCreator = new PaperReceiptCreator();
        String paperReceipt = paperReceiptCreator.createPaperReceipt(receiptMock);
        //System.out.println(paperReceipt);
        assertEquals(paperReceipt, "Paragon Fiskalny\nbeer\t20.0*2.39\t47.80\n\n\nRazem: 47.8zł");
    }

    @Test
    public void createPaperReceipt_EmptyValues_ReturnPaperReceiptProperly() {
        List<ProductForSale> psList = new ArrayList<>();
        psList.add(new ProductForSale());
        Mockito.when((receiptMock).getProductForSales()).thenReturn(psList);

        PaperReceiptCreator paperReceiptCreator = new PaperReceiptCreator();
        String paperReceipt = paperReceiptCreator.createPaperReceipt(receiptMock);
        //System.out.println(paperReceipt);
        assertEquals(paperReceipt, "Paragon Fiskalny\n\t0.0*0.0\t0.0\n\n\nRazem: 0.0zł");
    }
}
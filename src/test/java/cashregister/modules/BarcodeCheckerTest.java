package cashregister.modules;

import cashregister.model.enums.ObjectType;
import cashregister.modules.interfaces.IBarcodeChecker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BarcodeCheckerTest {

    private IBarcodeChecker barcodeChecker;

    @Before
    public void setUp() {
        barcodeChecker = new BarcodeChecker();
    }

    @Test
    public void getObjectTypeByBarcode_BarcodeLength15_ReturnsUser() {
        assertEquals(ObjectType.User, barcodeChecker.getObjectTypeByBarcode("123412341234123"));
    }

    @Test
    public void getObjectTypeByBarcode_BarcodeLength13_ReturnsProduct() {
        assertEquals(ObjectType.Product, barcodeChecker.getObjectTypeByBarcode("1234123412341"));
    }

    @Test
    public void getObjectTypeByBarcode_BarcodeLength14_ReturnsUnknown() {
        assertEquals(ObjectType.Unknown, barcodeChecker.getObjectTypeByBarcode("12341234123412"));

    }
}
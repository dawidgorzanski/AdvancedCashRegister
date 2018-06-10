package cashregister.barcode;

import cashregister.controllers.MainWindowController;
import cashregister.modules.ModulesManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



import static cashregister.barcode.BarcodeReader.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class BarcodeReaderTest {

    @Mock
    IBarcodeReaderDataListener listener1, listener2, listener3;

    @Test
    public void initializeBarcode_NormalUse_ReturnTrue() {
        assertTrue(initializeBarcode(""));
    }

    @Test
    public void addListener_IsInitialized_ReturnTrue() {
        ModulesManager.initialize();
        MainWindowController listener = new MainWindowController();

        initializeBarcode("");
        assertTrue(addListener(listener));
    }

    @Test
    public void addListener_NotInitialized_ReturnFalse() {
        ModulesManager.initialize();
        IBarcodeReaderDataListener listener = new MainWindowController();

        assertFalse(addListener(listener));
    }

}
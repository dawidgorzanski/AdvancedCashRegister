package cashregister.gui;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;

public class MainWindowController implements IBarcodeReaderDataListener {

    public MainWindowController() {
        BarcodeReader.addListener(this);
    }

    @FXML
    private TextArea textAreaTest;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        textAreaTest.appendText("BLEBLEBLE\n");
    }

    @Override
    public void barcodeValueArrived(String value) {
        textAreaTest.appendText(value );
    }
}

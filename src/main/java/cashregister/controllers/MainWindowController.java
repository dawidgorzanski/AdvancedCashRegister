package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;

public class MainWindowController implements IBarcodeReaderDataListener {

    IProductsListModule productsListModule;
    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
    }

    @FXML
    private TextArea textAreaTest;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String text = productsListModule.testMethod();
        textAreaTest.appendText(text);
    }

    @Override
    public void barcodeValueArrived(String value) {
        textAreaTest.appendText(value );
    }
}
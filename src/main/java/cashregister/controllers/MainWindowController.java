package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.model.Product;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class MainWindowController implements IBarcodeReaderDataListener {

    IProductsListModule productsListModule;

    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
    }

    @FXML
    private TextField textFieldDisplay;
    @FXML
    private ListView listViewProducts = new ListView();
    @FXML
    private ListView listViewPrices = new ListView();

    @FXML
    private void handleEnterButtonAction(ActionEvent event) {
        String value = textFieldDisplay.getText();
        int number;

        try {
            number = Integer.parseInt(value);
            productsListModule.addProduct(number);

            ObservableList<Product> products = productsListModule.getShoppingList();
            ObservableList<String> names = FXCollections.observableArrayList();
            ObservableList<String> prices = FXCollections.observableArrayList();

            for (Product item : products) {
                names.add(item.getName());
                String text = Integer.toString(item.getQuantity()) + "  X  CENA";
                prices.add(text);
            }
            listViewProducts.setItems(names);
            listViewPrices.setItems(prices);
            listViewProducts.refresh();
            listViewPrices.refresh();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Wpisz poprawny kod");

            alert.showAndWait();
        }
        textFieldDisplay.clear();
    }

    @FXML
    private void handleNumberButtonAction(ActionEvent event) {
        String text = ((Button) event.getSource()).getText();
        textFieldDisplay.appendText(text);
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        textFieldDisplay.clear();
    }

    @FXML
    private void handleFinalizeButtonAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/PaymentWindow.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.show();
    }

    @Override
    public void barcodeValueArrived(String value) {

    }

}
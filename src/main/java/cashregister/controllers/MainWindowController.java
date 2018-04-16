package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.model.ProductForSale;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController implements IBarcodeReaderDataListener {

    @FXML
    private TextField textFieldDisplay;
    @FXML
    private TableView<ProductForSale> tableViewProducts;
    @FXML
    private TableColumn<ProductForSale, String> tableColumnName;
    @FXML
    private TableColumn<ProductForSale, Double> tableColumnPrice;
    @FXML
    private TableColumn<ProductForSale, Double> tableColumnQuantity;

    private IProductsListModule productsListModule;

    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
    }

    @FXML
    private void initialize() {
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tableColumnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        /*tableColumnQuantity.setOnEditCommit(new EventHandler<CellEditEvent<Product, Double>>() {
            @Override
            public void handle(CellEditEvent<Product, Double> t) {
                ((Product) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setQuantity(t.getNewValue());
            }
        });*/

        tableViewProducts.setItems(productsListModule.getShoppingList());
    }


    @FXML
    private void handleEnterButtonAction(ActionEvent event) {
        String value = textFieldDisplay.getText();

        try {
            productsListModule.addProduct(value);

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
    private void handleDeleteButtonAction(ActionEvent event) {
        ProductForSale productToDelete =  tableViewProducts.getSelectionModel().selectedItemProperty().get();
        productsListModule.deleteProduct(productToDelete);
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleAddClientButtonAction(ActionEvent actionEvent) throws IOException{
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/NewClientWindow.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void barcodeValueArrived(String value) {

    }


}
package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.extensions.DialogResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
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
    @FXML
    private TableColumn<ProductForSale, Double> tableColumnTotalPrice;

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
        tableColumnTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        tableViewProducts.setItems(productsListModule.getShoppingList());
    }


    @FXML
    private void handleEnterButtonAction(ActionEvent event) throws IOException{
        String value = textFieldDisplay.getText();

         try {
             if (value.length()!=6 )
                 productsListModule.addProduct(value);
             else
                 displayCustomer(value);
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

    @FXML
    private void handleQuantityButtonAction(ActionEvent actionEvent) throws IOException {
        ProductForSale productToEdit = tableViewProducts.getSelectionModel().selectedItemProperty().get();
        if (productToEdit == null) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditQuantityWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditQuantityWindowController controller = (EditQuantityWindowController)fxmlLoader.getController();
        controller.setQuantity(productToEdit.getQuantity());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(200);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (controller.getDialogResult().equals(DialogResult.OK)) {
            if (productToEdit.getCountable()) {
                productToEdit.setQuantity((int)controller.getQuantity());
            }
            else {
                productToEdit.setQuantity(controller.getQuantity());
            }
        }
    }

    @Override
    public void barcodeValueArrived(String value) {

    }

    private void displayCustomer(String barcode) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DisplayCustomerWindow.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));

        Customer customer = new Customer(1, "Name", "111111", "mail@m.l", "address", "123456789");
        // to do: odczyt danych z Customer na podstawie barcode

        DisplayCustomerWindowController controller = loader.<DisplayCustomerWindowController>getController();
        controller.initData(customer);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.show();

    }


}
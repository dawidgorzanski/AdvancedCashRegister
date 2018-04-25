package cashregister.controllers;

import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import cashregister.model.enums.ObjectType;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IBarcodeChecker;
import cashregister.modules.interfaces.ICustomerModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.extensions.DialogResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController implements IBarcodeReaderDataListener {

    @FXML
    private TextField textFieldDisplay;
    @FXML
    private Label labelTotalPrice;
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
    @FXML
    private Button enter, finish;

    private IProductsListModule productsListModule;
    private IBarcodeChecker barcodeChecker;
    private ICustomerModule customerModule;

    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.barcodeChecker = ModulesManager.getObjectByType(IBarcodeChecker.class);
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
    }

    @FXML
    private void initialize() throws IOException {
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tableColumnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tableColumnTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        tableViewProducts.setItems(productsListModule.getShoppingList());
    }

    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            enter.fire();
            return;
        }
    }

    @FXML
    private void handleEnterButtonAction(ActionEvent event) throws IOException {
        String value = textFieldDisplay.getText();
        handleBarcode(value);

        textFieldDisplay.clear();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        ProductForSale productToDelete =  tableViewProducts.getSelectionModel().selectedItemProperty().get();
        productsListModule.deleteProduct(productToDelete);
        updateTotalPrice();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PaymentWindow.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
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
            updateTotalPrice();
        }
    }

    @Override
    public void barcodeValueArrived(String value) {
        handleBarcode(value);
    }

    private void handleBarcode(String value) {
        ObjectType objectType = barcodeChecker.getObjectTypeByBarcode(value);
        switch (objectType)
        {
            case Product:
            {
                productsListModule.addProduct(value);
                updateTotalPrice();
                break;
            }
            case User:
            {
                Customer customer = customerModule.getCustomerByBarcode(value);
                if (customer != null) {
                    productsListModule.setCustomerForTransaction(customer);
                    showCustomerAddedDialog(customer.getName());
                }

                break;
            }
        }
    }

    private void showCustomerAddedDialog(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Użytkownik został dodany");
        alert.setHeaderText("Użytkownik został dodany");
        alert.setContentText(String.format("Użytkownik %s został dodany.", username));
        alert.showAndWait();
    }

    private void updateTotalPrice() {
        this.labelTotalPrice.setText("SUMA: " + String.valueOf(productsListModule.getTotalPrice()) + " PLN");
    }

    private void displayCustomer(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DisplayCustomerWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));

            DisplayCustomerWindowController controller = loader.<DisplayCustomerWindowController>getController();
            controller.initData(customer);
            stage.setWidth(750);
            stage.setHeight(650);
            stage.show();
        }
        catch (IOException ex) {

        }
    }
}
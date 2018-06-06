package cashregister.controllers;

import cashregister.Main;
import cashregister.barcode.BarcodeReader;
import cashregister.barcode.IBarcodeReaderDataListener;
import cashregister.dao.ProductDefinitionDao;
import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.ProductForSale;
import cashregister.model.User;
import cashregister.model.enums.ObjectType;
import cashregister.modules.ModulesManager;
import cashregister.modules.ProductDefinitionModule;
import cashregister.modules.interfaces.*;
import javafx.application.Platform;
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
import org.decimal4j.util.DoubleRounder;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController implements IBarcodeReaderDataListener {

    @FXML
    private TextField textFieldDisplay;
    @FXML
    private Label labelTotalPrice, cashier_name, customer_name;
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
    private Button enter, delete, finish, quantity, search, new_client, admin, backspace, display_customer, btnRemoveUserFromTransaction;

    private IProductsListModule productsListModule;
    private IBarcodeChecker barcodeChecker;
    private ICustomerModule customerModule;
    private IAuthenticationModule authenticationModule;

    public MainWindowController() {
        BarcodeReader.addListener(this);
        this.productsListModule = ModulesManager.getObjectByType(IProductsListModule.class);
        this.barcodeChecker = ModulesManager.getObjectByType(IBarcodeChecker.class);
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
        this.authenticationModule = ModulesManager.getObjectByType(IAuthenticationModule.class);
    }

    @FXML
    private void initialize() throws IOException {
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tableColumnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tableColumnTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        tableViewProducts.setItems(productsListModule.getShoppingList());

        User logggedUser = authenticationModule.getLoggedUser();
        initCashierData(logggedUser.getName());
        showAdminButton(logggedUser.getIsAdmin());
    }

    private void initCashierData(String username) {
        cashier_name.setText(username);
    }

    private void showAdminButton(boolean value) { admin.setVisible(value); }

    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            enter.fire();
            return;
        }
        if (keyCode.equals(KeyCode.DELETE) || keyCode.equals(KeyCode.D)) {
            delete.fire();
            return;
        }
        if (keyCode.equals(KeyCode.X)) {
            quantity.fire();
            return;
        }
        if (keyCode.equals(KeyCode.Z)) {
            finish.fire();
            return;
        }
        if (keyCode.equals(KeyCode.W)) {
            search.fire();
            return;
        }
        if (keyCode.equals(KeyCode.N)) {
            new_client.fire();
            return;
        }
        if (keyCode.isDigitKey() || keyCode.equals(KeyCode.PERIOD)) {
            String text = keyCode.getChar();
            textFieldDisplay.appendText(text);
            return;
        }
        if (keyCode.equals(KeyCode.C)) {
            textFieldDisplay.clear();
            return;
        }
        if (keyCode.equals(KeyCode.A)) {
            if(admin.isVisible())
            admin.fire();
            return;
        }
        if (keyCode.equals(KeyCode.BACK_SPACE)) {
            backspace.fire();
            return;
        }
    }

    @FXML
    private void handleEnterButtonAction(ActionEvent event) throws IOException {
        String value = textFieldDisplay.getText();
        if(!value.equals(""))
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
    private void handleBackspaceButtonAction(ActionEvent event) {
        String text = textFieldDisplay.getText();
        textFieldDisplay.setText(text.substring(0, text.length() - 1));
    }

    @FXML
    private void handleNumberButtonAction(ActionEvent event) {
        String text = ((Button) event.getSource()).getText();
        textFieldDisplay.appendText(text);
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent actionEvent) throws IOException
    {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/LoginWindow.fxml")));
        Stage primaryStage = Main.getPrimaryStage();
        primaryStage.setScene(scene);
        primaryStage.setWidth(300);
        primaryStage.setHeight(300);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        textFieldDisplay.clear();
    }

    @FXML
    private void handleFinalizeButtonAction(ActionEvent event) throws IOException {
        if(productsListModule.getShoppingList().isEmpty())
            return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PaymentWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        PaymentWindowController controller = (PaymentWindowController)fxmlLoader.getController();
        controller.setMainWindowController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (controller.getDialogResult().equals(DialogResult.OK)) {
            updateCurrentCustomerForTransaction(productsListModule.getCurrentCustomer());
        }
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
    private void handleDisplayCustomerButtonAction(ActionEvent actionEvent){
        if (productsListModule.getCurrentCustomer() != null)
            displayCustomerData(productsListModule.getCurrentCustomer());
    }

    @FXML
    private void handleRemoveCustomerFromTransaction(ActionEvent actionEvent) {
        productsListModule.deleteCustomerFromTransaction();
        updateCurrentCustomerForTransaction(productsListModule.getCurrentCustomer());
    }

    @FXML
    private void handleAdminButtonAction(ActionEvent actionEvent) throws IOException
    {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminWindow.fxml")));
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
                productToEdit.setQuantity(DoubleRounder.round(controller.getQuantity(),3));
            }
            updateTotalPrice();
        }
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent actionEvent) throws IOException{
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/SearchWindow.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void handleCancelTransaction(ActionEvent actionEvent) {
        productsListModule.deleteCustomerFromTransaction();
        productsListModule.deleteAllProducts();
        updateCurrentCustomerForTransaction(productsListModule.getCurrentCustomer());
    }

    @Override
    public void barcodeValueArrived(String value) {
        handleBarcode(value.replace("\r\n", ""));
    }

    private void handleBarcode(String value) {
        ObjectType objectType = barcodeChecker.getObjectTypeByBarcode(value);
        switch (objectType)
        {
            case Product:
            {
                ProductDefinition product= productsListModule.getByBarcode(value);
                if(product != null) {
                    boolean ageLimit = product.getAgeLimit();
                    if (ageLimit)
                        showAgeLimitWarning();
                    productsListModule.addProduct(value);
                    updateTotalPrice();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd");
                    alert.setContentText("Produkt nie znajduje się w bazie danych");
                    alert.showAndWait();
                }

                break;
            }
            case Unknown:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd");
                alert.setContentText("Nieprawidłowy kod");
                alert.showAndWait();

                break;
            case User:
            {
                Customer customer = customerModule.getCustomerByBarcode(value);
                if (customer != null) {
                    productsListModule.setCustomerForTransaction(customer);
                    updateCurrentCustomerForTransaction(customer);
                }

                break;
            }
        }
    }

    private void showAgeLimitWarning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ograniczenie wiekowe");
        alert.setHeaderText("Uwaga! Produkt ma ograniczenie wiekowe.");
        alert.setContentText("Sprawdź czy klient ma ukończone 18 lat.\nW przeciwnym wypadku usuń produkt z listy.");
        alert.showAndWait();
    }

    public void updateTotalPrice() {
        this.labelTotalPrice.setText("SUMA: " + String.valueOf(DoubleRounder.round(productsListModule.getTotalPrice(),2)) + " PLN");
    }

    private void displayCustomerData(Customer customer) {
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

    private void updateCurrentCustomerForTransaction(Customer customer) {
        if (customer != null) {
            Platform.runLater(() -> {
                customer_name.setText(customer.getName());
                display_customer.setVisible(true);
                btnRemoveUserFromTransaction.setVisible(true);
            });
        }
        else {
            Platform.runLater(() -> {
                customer_name.setText("");
                display_customer.setVisible(false);
                btnRemoveUserFromTransaction.setVisible(false);
            });
        }
    }
}
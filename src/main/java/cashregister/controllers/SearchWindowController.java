package cashregister.controllers;

import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.ICustomerModule;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class SearchWindowController {

    @FXML
    private TableView<ProductDefinition> tableViewProducts;
    @FXML
    private TableView<Customer> tableViewCustomers;
    @FXML
    private TableColumn<ProductDefinition, String> productName, productBarcode;
    @FXML
    private TableColumn<ProductDefinition, Double> productPrice, productQuantity;
    @FXML
    private TableColumn<Customer, String> customerName, customerBarcode, customerAddress, customerPhone, customerMail;
    @FXML
    private TextField searchField;
    @FXML
    private Button enter, exit;
    @FXML
    private TabPane tabPane;

    private IProductDefinitionModule productDefinitionModule;
    private ICustomerModule customerModule;

    public SearchWindowController()
    {
        this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
    }

    @FXML
    private void initialize() throws IOException {
        productName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        productQuantity.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getQuantity()));
        productPrice.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));
        productBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));

        customerName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        customerAddress.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAddress()));
        customerBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        customerMail.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMail()));
        customerPhone.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
    }

    @FXML
    private void handleKeyAction(KeyEvent key) {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            enter.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            exit.fire();
            return;
        }
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent actionEvent) throws IOException {

        if(tabPane.getSelectionModel().getSelectedIndex() == 0) {
            tableViewProducts.setItems(productDefinitionModule.getByName(searchField.getText()));
        }
        else {
            tableViewCustomers.setItems(customerModule.getByNameBarcodeOrMail(searchField.getText()));
        }
        searchField.clear();
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) throws IOException {
        if(tabPane.getSelectionModel().getSelectedIndex() == 0) {
            ProductDefinition productToAdd = tableViewProducts.getSelectionModel().selectedItemProperty().get();
            cashregister.Main.getMainWindowController().barcodeValueArrived(productToAdd.getBarcode());
        }
        else
        {
            Customer customerToAdd = tableViewCustomers.getSelectionModel().selectedItemProperty().get();
            cashregister.Main.getMainWindowController().barcodeValueArrived(customerToAdd.getBarcode());
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}

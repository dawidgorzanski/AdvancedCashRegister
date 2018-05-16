package cashregister.controllers;

import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.ICustomerModule;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IUserModule;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminWindowController {

    @FXML
    private TableView<ProductDefinition> tableViewProducts;
    @FXML
    private TableView<Customer> tableViewCustomers;
    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<ProductDefinition, String> productName, productBarcode;
    @FXML
    private TableColumn<ProductDefinition, Double> productPrice, productQuantity;
    @FXML
    private TableColumn<Customer, String> customerName, customerBarcode, customerAddress, customerPhone, customerMail;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, Boolean> isAdmin;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button exit, add, edit, delete;

    private IProductDefinitionModule productDefinitionModule;
    private ICustomerModule customerModule;
    private IUserModule userModule;

    public AdminWindowController()
    {
        this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
        this.userModule = ModulesManager.getObjectByType(IUserModule.class);
    }

    @FXML
    private void initialize() {
        productName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        productQuantity.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getQuantity()));
        productPrice.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));
        productBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        tableViewProducts.setItems(productDefinitionModule.getAllProducts());

        customerName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        customerAddress.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAddress()));
        customerBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        customerMail.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMail()));
        customerPhone.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
        tableViewCustomers.setItems(customerModule.getAllCustomers());

        userName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        isAdmin.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIsAdmin()));
        tableViewUsers.setItems(userModule.getAllUsers());
    }

    @FXML
    private void handleAddButtonAction(ActionEvent actionEvent) throws IOException{

        String path = this.getPath();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(path)));
        this.loadStage(scene, actionEvent);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleEditButtonAction(ActionEvent actionEvent) throws IOException{

        String path = this.getPath();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
        {
            ProductDefinition productToEdit = tableViewProducts.getSelectionModel().selectedItemProperty().get();
            NewProductWindowController controller = (NewProductWindowController)fxmlLoader.getController();
            controller.setNameField(productToEdit.getName());
            controller.setBarcodeField(productToEdit.getBarcode());
            controller.setPriceField(productToEdit.getPrice());
            controller.setQuantityField(productToEdit.getQuantity());
            controller.setCountable(productToEdit.getCountable());
            controller.setAgeRestricted(productToEdit.getAgeLimit());
            controller.changeText();
            controller.setProduct(productToEdit);
        }
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
        {
            Customer customerToEdit = tableViewCustomers.getSelectionModel().selectedItemProperty().get();
            NewClientWindowController controller = (NewClientWindowController)fxmlLoader.getController();
            controller.setNameField(customerToEdit.getName());
            controller.setBarcodeField(customerToEdit.getBarcode());
            controller.setAddressField(customerToEdit.getAddress());
            controller.setPhoneField(customerToEdit.getPhone());
            controller.setMailField(customerToEdit.getMail());
            controller.changeText();
            controller.setCustomer(customerToEdit);
        }
        else
        {
            User userToEdit = tableViewUsers.getSelectionModel().selectedItemProperty().get();
            NewUserWindowController controller = (NewUserWindowController)fxmlLoader.getController();
            controller.setNameField(userToEdit.getName());
            controller.setIsAdmin(userToEdit.getIsAdmin());
            controller.setUser(userToEdit);
            controller.changeText();
        }

        this.loadStage(scene, actionEvent);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    private void loadStage(Scene scene, ActionEvent actionEvent)
    {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private String getPath() {
        String path = "";
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
            path = "/fxml/NewProductWindow.fxml";
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
            path = "/fxml/NewClientWindow.fxml";
        else
            path = "/fxml/NewUserWindow.fxml";

        return path;
    }

    public void refreshScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminWindow.fxml"));
        Scene refreshScene = new Scene(loader.load());
        loadStage(refreshScene,event);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent actionEvent) throws IOException {
        String path = this.getPath();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
        {
            ProductDefinition productToDelete = tableViewProducts.getSelectionModel().selectedItemProperty().get();
            NewProductWindowController controller = (NewProductWindowController)fxmlLoader.getController();
            controller.deleteProduct(productToDelete);
            this.refreshScene(actionEvent);
        }
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
        {
            Customer customerToDelete = tableViewCustomers.getSelectionModel().selectedItemProperty().get();
            NewClientWindowController controller = (NewClientWindowController)fxmlLoader.getController();
            controller.deleteCustomer(customerToDelete);
            this.refreshScene(actionEvent);
        }
        else
        {
            User userToDelete = tableViewUsers.getSelectionModel().selectedItemProperty().get();
            NewUserWindowController controller = (NewUserWindowController)fxmlLoader.getController();
            controller.deleteUser(userToDelete);
            this.refreshScene(actionEvent);
        }

    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}

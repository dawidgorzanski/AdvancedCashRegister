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
    private TableColumn<ProductDefinition, String> tableColumnProductName, tableColumnProductBarcode;
    @FXML
    private TableColumn<ProductDefinition, Double> tableColumnProductPrice, tableColumnProductQuantity;
    @FXML
    private TableColumn<Customer, String> tableColumnCustomerName, tableColumnCustomerBarcode, tableColumnCustomerAddress, tableColumnCustomerPhone, tableColumnCustomerMail;
    @FXML
    private TableColumn<User, String> tableColumnUserName;
    @FXML
    private TableColumn<User, Boolean> tableColumnIsAdmin;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button btnExit, btnAdd, btnEdit, btnDelete;

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
        tableColumnProductName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        tableColumnProductQuantity.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getQuantity()));
        tableColumnProductPrice.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));
        tableColumnProductBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        tableViewProducts.setItems(productDefinitionModule.getAllProducts());

        tableColumnCustomerName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        tableColumnCustomerAddress.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAddress()));
        tableColumnCustomerBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        tableColumnCustomerMail.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMail()));
        tableColumnCustomerPhone.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhone()));
        tableViewCustomers.setItems(customerModule.getAllCustomers());

        tableColumnUserName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        tableColumnIsAdmin.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIsAdmin()));
        tableViewUsers.setItems(userModule.getAllUsers());
    }

    @FXML
    private void handleAddButtonAction(ActionEvent actionEvent) throws IOException{

        String path = this.getPath();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(path)));
        this.loadStage(scene, actionEvent);
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
        {
            tableViewProducts.setItems(productDefinitionModule.getAllProducts());
        }
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
        {
            tableViewCustomers.setItems(customerModule.getAllCustomers());
        }
        else
        {
            tableViewUsers.setItems(userModule.getAllUsers());
        }
    }

    @FXML
    private void handleEditButtonAction(ActionEvent actionEvent) throws IOException{

        String path = this.getPath();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(fxmlLoader.load());
        boolean edit = true;
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
        {
            ProductDefinition productToEdit = tableViewProducts.getSelectionModel().selectedItemProperty().get();
            NewProductWindowController controller = (NewProductWindowController)fxmlLoader.getController();
            controller.setProductDefinition(productToEdit);
            this.loadStage(scene, actionEvent);
            tableViewProducts.setItems(productDefinitionModule.getAllProducts());
        }
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
        {
            Customer customerToEdit = tableViewCustomers.getSelectionModel().selectedItemProperty().get();
            NewClientWindowController controller = (NewClientWindowController)fxmlLoader.getController();
            controller.setCustomer(customerToEdit);
            this.loadStage(scene, actionEvent);
            tableViewCustomers.setItems(customerModule.getAllCustomers());
        }
        else
        {
            User userToEdit = tableViewUsers.getSelectionModel().selectedItemProperty().get();
            NewUserWindowController controller = (NewUserWindowController)fxmlLoader.getController();
            controller.setUser(userToEdit);
            this.loadStage(scene, actionEvent);
            tableViewUsers.setItems(userModule.getAllUsers());
        }
    }

    private void loadStage(Scene scene, ActionEvent actionEvent)
    {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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

    @FXML
    private void handleDeleteButtonAction(ActionEvent actionEvent) throws IOException {
        String path = this.getPath();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.load();
        if(tabPane.getSelectionModel().getSelectedIndex() == 0)
        {
            ProductDefinition productToDelete = tableViewProducts.getSelectionModel().selectedItemProperty().get();
            if (productToDelete == null)
                return;

            NewProductWindowController controller = (NewProductWindowController)fxmlLoader.getController();
            controller.deleteProduct(productToDelete);
            tableViewProducts.setItems(productDefinitionModule.getAllProducts());
        }
        else if(tabPane.getSelectionModel().getSelectedIndex() == 1)
        {
            Customer customerToDelete = tableViewCustomers.getSelectionModel().selectedItemProperty().get();
            if (customerToDelete == null)
                return;

            customerModule.deleteCustomer(customerToDelete);
            tableViewCustomers.setItems(customerModule.getAllCustomers());
        }
        else
        {
            User userToDelete = tableViewUsers.getSelectionModel().selectedItemProperty().get();
            if (userToDelete == null)
                return;

            NewUserWindowController controller = (NewUserWindowController)fxmlLoader.getController();
            userModule.deleteUser(userToDelete);
            tableViewUsers.setItems(userModule.getAllUsers());
        }

    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}

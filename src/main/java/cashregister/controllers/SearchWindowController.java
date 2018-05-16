package cashregister.controllers;

import cashregister.model.ProductDefinition;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductDefinitionModule;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class SearchWindowController {

    @FXML
    private TableView<ProductDefinition> tableViewProducts;
    @FXML
    private TableColumn<ProductDefinition, String> productName, productBarcode;
    @FXML
    private TableColumn<ProductDefinition, Double> productPrice, productQuantity;
    @FXML
    private TextField searchField;
    @FXML
    private Button enter, exit;

    private IProductDefinitionModule productDefinitionModule;

    public SearchWindowController()
    {
        this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
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
        productName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        productQuantity.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getQuantity()));
        productPrice.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));
        productBarcode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBarcode()));
        tableViewProducts.setItems(productDefinitionModule.getByName(searchField.getText()));
    }


}

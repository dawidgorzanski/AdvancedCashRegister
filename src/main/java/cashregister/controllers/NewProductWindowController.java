package cashregister.controllers;


import cashregister.model.Customer;
import cashregister.model.ProductDefinition;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.ICustomerModule;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class NewProductWindowController {

    private IProductDefinitionModule productDefinitionModule;
    private ProductDefinition product;
    public NewProductWindowController() { this.productDefinitionModule = ModulesManager.getObjectByType(IProductDefinitionModule.class);
    }

    @FXML
    private TextField nameField, barcodeField, quantityField, priceField;
    @FXML
    private Button enter, cancel;
    @FXML
    private ComboBox isCountable, isAgeRestricted;
    @FXML
    private Text title;

    public void setProduct (ProductDefinition product) { this.product = product; }
    public void setNameField(String name) { nameField.setText(name); }
    public void setBarcodeField(String barcode) { barcodeField.setText(barcode); }
    public void setQuantityField(Double quantity) { quantityField.setText(quantity.toString()); }
    public void setPriceField(Double price) { priceField.setText(price.toString()); }
    public void setCountable(Boolean countable) {
        if(countable == true)
        isCountable.getSelectionModel().select(0);
        else
            isCountable.getSelectionModel().select(1);
    }
    public void setAgeRestricted(Boolean restricted) {
        if(restricted == true)
            isAgeRestricted.getSelectionModel().select(0);
        else
            isAgeRestricted.getSelectionModel().select(1);
    }
    public void changeText() { enter.setText("Zmień"); title.setText("Edytuj produkt"); }

    public void deleteProduct(ProductDefinition product) { productDefinitionModule.deleteProduct(product);}

    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            enter.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            cancel.fire();
            return;
        }
    }

    private void exitAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminWindow.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(750);
        stage.setHeight(650);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {

        if(product == null)
            product = new ProductDefinition();
        product.setName(nameField.getText());
        product.setQuantity(Double.parseDouble(quantityField.getText()));
        product.setBarcode(barcodeField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        if(isCountable.getValue().equals("TAK"))
            product.setCountable(true);
        else
            product.setCountable(false);
        if(isAgeRestricted.getValue().equals("TAK"))
            product.setAgeLimit(true);
        else
            product.setAgeLimit(false);

        productDefinitionModule.addProduct(product);
        exitAction(event);

    }

    @FXML
    private void initialize()
    {
        isCountable.getItems().addAll("TAK", "NIE");
        isAgeRestricted.getItems().addAll("TAK", "NIE");
    }

}

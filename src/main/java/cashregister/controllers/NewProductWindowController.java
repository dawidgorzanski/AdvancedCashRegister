package cashregister.controllers;


import cashregister.helpers.ValidatorHelper;
import cashregister.model.ProductDefinition;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductDefinitionModule;
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
import org.decimal4j.util.DoubleRounder;

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

    public void setProductDefinition(ProductDefinition product) {
        this.product = product;
        if (product != null) {
            nameField.setText(product.getName());
            barcodeField.setText(product.getBarcode());
            quantityField.setText(String.valueOf(product.getQuantity()));
            priceField.setText(String.valueOf(product.getPrice()));

            if (product.getCountable()) {
                isCountable.getSelectionModel().select(0);
            }
            else {
                isCountable.getSelectionModel().select(1);
            }

            if(product.getAgeLimit()) {
                isAgeRestricted.getSelectionModel().select(0);
            }
            else {
                isAgeRestricted.getSelectionModel().select(1);
            }

            title.setText("Edytuj produkt");
        }
    }

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
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {

        if (!ValidatorHelper.validateNameNotEmpty(nameField.getText())) {
            showErrorAlert("Pole nazwa jest wymagane!");
            return;
        }

        if (!ValidatorHelper.validateProductBarcodeLength(barcodeField.getText())){
            showErrorAlert("Kod kreskowy powinien składać się z 13 znaków!");
            return;
        }

        if (!ValidatorHelper.validateQuantity(quantityField.getText())) {
            showErrorAlert("Niepoprawna ilość!");
            return;
        }

        if (!ValidatorHelper.validatePrice(priceField.getText())) {
            showErrorAlert("Niepoprawna cena!");
            return;
        }

        if (!ValidatorHelper.validateIsNotNull(isCountable.getValue())) {
            showErrorAlert("Jeden z typów: 'policzalny' lub 'niepoliczalny' musi zostać wybrany!");
            return;
        }

        if (!ValidatorHelper.validateIsNotNull(isAgeRestricted.getValue())) {
            showErrorAlert("Jeden z typów: 'z ograniczeniami wiekowymi' lub 'bez ograniczeń wiekowych' musi zostać wybrany!");
            return;
        }

        if(product == null)
            product = new ProductDefinition();

        product.setName(nameField.getText());
        product.setQuantity(Double.parseDouble(quantityField.getText()));
        product.setBarcode(barcodeField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        if(isCountable.getValue().equals("TAK")) {
            product.setCountable(true);
            Double d = Double.parseDouble(quantityField.getText());
            Integer i = (Integer)d.intValue();
            product.setQuantity(i);
        }
        else{
            product.setCountable(false);
            product.setQuantity(DoubleRounder.round(Double.parseDouble(quantityField.getText()),3));
        }
        if(isAgeRestricted.getValue().equals("TAK")) {
            product.setAgeLimit(true);
        }
        else {
            product.setAgeLimit(false);
        }

        productDefinitionModule.addProduct(product);
        exitAction(event);
    }

    private void showErrorAlert(String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText(context);
        alert.showAndWait();
    }

    @FXML
    private void initialize()
    {
        isCountable.getItems().addAll("TAK", "NIE");
        isAgeRestricted.getItems().addAll("TAK", "NIE");
    }

}

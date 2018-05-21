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
    private boolean edit;
    private String oldBarcode;

    public void setOldBarcode(String oldBarcode) { this.oldBarcode = oldBarcode; }
    public void setEdit(boolean edit){ this.edit=edit; }
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AdminWindowController controller = (AdminWindowController)fxmlLoader.getController();
        controller.refreshScene(event);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    private boolean validateBarcode() {
        String barcode = barcodeField.getText();
        ProductDefinition p = productDefinitionModule.getByBarcode(barcode);

        if (!edit && p == null)
            return true;
        if (edit && !barcode.equals(oldBarcode) && p == null)
            return true;
        if (edit && barcode.equals(oldBarcode))
            return true;

        return false;
    }

    private boolean validateInput() {
        String barcode = barcodeField.getText();
        String quantity = quantityField.getText();
        String name = nameField.getText();
        String price = priceField.getText();
        Object count = isCountable.getValue();
        Object age = isAgeRestricted.getValue();

        if (StringUtils.isNumeric(barcode) && barcode.length() == 13 && name.length() > 0 && count != null && age != null && quantity.length() > 0 && price.length() > 0)
            return true;

        return false;
    }

    private boolean validateNumbers(){
        try{
            Double quantity = Double.parseDouble(quantityField.getText());
            Double price = Double.parseDouble(priceField.getText());
        }
        catch (Exception e1){
            return false;
        }
        return true;
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {

        if (!validateBarcode()){
            showBarcodeAlert();
            return;
        }
        if (!validateInput()) {
            showAlert();
            return;
        }
        if (!validateNumbers()) {
            showNumberAlert();
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
        if(isAgeRestricted.getValue().equals("TAK"))
            product.setAgeLimit(true);
        else
            product.setAgeLimit(false);

        productDefinitionModule.addProduct(product);
        exitAction(event);

    }

    private void showBarcodeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Ten kod kreskowy znajduje się już w bazie danych.");
        alert.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę uzupełnić wszystkie pola.\nKod kreskowy musi składać się z 13 cyfr.");
        alert.showAndWait();
    }

    private void showNumberAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Pola: cena i ilość muszą być liczbami rzeczywistymi.\nJeśli produkt jest policzalny, ilość musi być liczbą całkowitą");
        alert.showAndWait();
    }

    @FXML
    private void initialize()
    {
        isCountable.getItems().addAll("TAK", "NIE");
        isAgeRestricted.getItems().addAll("TAK", "NIE");
    }

}

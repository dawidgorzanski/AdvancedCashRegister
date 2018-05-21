package cashregister.controllers;


import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class NewClientWindowController {

    private ICustomerModule customerModule;
    private Customer customer;
    public NewClientWindowController() {
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
    }

    @FXML
    private TextField nameField, barcodeField, mailField, addressField, phoneField;
    @FXML
    private Button enter, cancel;
    @FXML
    private Text title;
    private boolean edit;
    private String oldBarcode;

    public void setOldBarcode(String oldBarcode) { this.oldBarcode = oldBarcode; }
    public void setEdit(boolean edit) { this.edit = edit; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setNameField(String name) { nameField.setText(name); }
    public void setBarcodeField(String barcode) { barcodeField.setText(barcode); }
    public void setAddressField(String address) { addressField.setText(address); }
    public void setPhoneField(String phone) { phoneField.setText(phone); }
    public void setMailField(String mail) { mailField.setText(mail); }
    public void changeText() { enter.setText("Zmień"); title.setText("Edytuj klienta"); }
    public void deleteCustomer(Customer customer) { customerModule.deleteCustomer(customer); }

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

    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    private void exitAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AdminWindowController controller = (AdminWindowController)fxmlLoader.getController();
        controller.refreshScene(event);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę podać nazwę oraz 15-cyfrowy kod kreskowy.");
        alert.showAndWait();
    }

    private void showBarcodeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Ten kod kreskowy znajduje się już w bazie danych.");
        alert.showAndWait();
    }

    private boolean validateInput() {
        String barcode = barcodeField.getText();
        String name = nameField.getText();

        if (StringUtils.isNumeric(barcode) && barcode.length() == 15 && name.length() > 0)
            return true;

        return false;
    }

    private boolean validateBarcode() {
        String barcode = barcodeField.getText();
        Customer c = customerModule.getCustomerByBarcode(barcode);

        if (!edit && c == null)
            return true;
        if (edit && !barcode.equals(oldBarcode) && c == null)
            return true;
        if (edit && barcode.equals(oldBarcode))
            return true;

        return false;
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

        if(customer == null)
            customer = new Customer();
        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setBarcode(barcodeField.getText());
        customer.setMail(mailField.getText());
        customer.setPhone(phoneField.getText());

        customerModule.addCustomer(customer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AdminWindowController controller = (AdminWindowController) fxmlLoader.getController();
        controller.refreshScene(event);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}

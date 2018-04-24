package cashregister.controllers;


import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;


public class NewClientWindowController {

    private ICustomerModule customerModule;
    public NewClientWindowController() {
        this.customerModule = ModulesManager.getObjectByType(ICustomerModule.class);
    }

    @FXML
    private TextField nameField, barcodeField, mailField, addressField, phoneField;

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę podać nazwę oraz 15-cyfrowy kod kreskowy.");
        alert.showAndWait();
    }

    private boolean validateInput() {
        String barcode = barcodeField.getText();
        String name = nameField.getText();

        if (StringUtils.isNumeric(barcode) && barcode.length() == 15 && name.length() > 0)
            return true;

        return false;
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) {
        if (!validateInput()) {
            showAlert();
            return;
        }

        Customer customer = new Customer();
        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setBarcode(barcodeField.getText());
        customer.setMail(mailField.getText());
        customer.setPhone(phoneField.getText());

        customerModule.addCustomer(customer);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}

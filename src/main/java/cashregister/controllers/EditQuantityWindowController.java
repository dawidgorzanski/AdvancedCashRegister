package cashregister.controllers;
import cashregister.helpers.ConvertHelper;
import javafx.event.ActionEvent;
import javafx.extensions.DialogController;
import javafx.extensions.DialogResult;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Controller class for EditQuantityWindow
 */
public class EditQuantityWindowController extends DialogController {

    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tbQuantity;

    /**
     * Function is activated when OK button is clicked, function parses entered quantity value
     * @param event
     */
    @FXML
    private void handleOKButton(ActionEvent event) {
        if (!ConvertHelper.tryParsePositiveDouble(tbQuantity.getText())) {
            showAlert();
            return;
        }

        this.setDialogResult(DialogResult.OK);
        closeWindow();
    }

    /**
     * Method for handling key events. Closes current stage when ESCAPE key or OK button is clicked
     * @param key
     * @throws IOException
     */
    @FXML
    private void handleKeyAction(KeyEvent key) throws IOException {
        KeyCode keyCode = key.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            btnOk.fire();
            return;
        }
        if (keyCode.equals(KeyCode.ESCAPE)) {
            btnCancel.fire();
            return;
        }
    }

    /**
     * Function is activated when wrong quantity value was entered, function shows an alert
     * @param event
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę wpisać wartość będącą liczbą dodatnią!");
        alert.showAndWait();
    }

    /**
     * Function is activated when cancel button is clicked, function closes current stage
     * @param event
     */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        this.setDialogResult(DialogResult.Cancel);
        closeWindow();
    }

    /**
     * Function function closes current stage
     */
    private void closeWindow() {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets quantity of product
     * @param quantity
     */
    public void setQuantity(double quantity) {
        tbQuantity.setText(String.valueOf(quantity));
    }

    /**
     *
     * @return quantity of product
     */
    public double getQuantity() {
        if (ConvertHelper.tryParsePositiveDouble(tbQuantity.getText())) {
            return Double.parseDouble(tbQuantity.getText());
        }

        return -1;
    }
}

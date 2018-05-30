package cashregister.controllers;
import cashregister.helpers.ConvertHelper;
import javafx.event.ActionEvent;
import javafx.extensions.DialogController;
import javafx.extensions.DialogResult;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class EditQuantityWindowController extends DialogController {

    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tbQuantity;

    @FXML
    private void handleOKButton(ActionEvent event) {
        if (!ConvertHelper.tryParsePositiveDouble(tbQuantity.getText())) {
            showAlert();
            return;
        }

        this.setDialogResult(DialogResult.OK);
        closeWindow();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę wpisać wartość będącą liczbą dodatnią!");
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        this.setDialogResult(DialogResult.Cancel);
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void setQuantity(double quantity) {
        tbQuantity.setText(String.valueOf(quantity));
    }

    public double getQuantity() {
        if (ConvertHelper.tryParsePositiveDouble(tbQuantity.getText())) {
            return Double.parseDouble(tbQuantity.getText());
        }

        return -1;
    }
}

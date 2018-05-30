package cashregister.controllers;


import cashregister.model.ProductDefinition;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductDefinitionModule;
import cashregister.modules.interfaces.IUserModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class NewUserWindowController {

    private IUserModule userModule;
    private User user;

    public NewUserWindowController() { this.userModule = ModulesManager.getObjectByType(IUserModule.class);
    }

    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button enter, cancel;
    @FXML
    private ComboBox isAdmin;
    @FXML
    private Text title;
    private boolean edit;
    private String oldName;

    public void setOldName(String oldName) { this.oldName = oldName; }
    public void setEdit(boolean edit) {this.edit = edit; }
    public void setUser(User user) {
        this.user = user;
    }
    public void setNameField(String name) { this.nameField.setText(name); }
    public void setIsAdmin(Boolean admin) {
        if(admin == true)
            isAdmin.getSelectionModel().select(0);
        else
            isAdmin.getSelectionModel().select(1);
    }
    public void changeText() { enter.setText("Zmień"); title.setText("Edytuj użytkownika"); }
    public void deleteUser(User user) { userModule.deleteUser(user); }

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
        controller.showScene(event);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
        exitAction(event);
    }

    private boolean validateName() {
        String name = nameField.getText();
        User u = userModule.getUserByUserName(name);

        if (!edit && u == null)
            return true;
        if (edit && !name.equals(oldName) && u == null)
            return true;
        if (edit && name.equals(oldName))
            return true;

        return false;
    }

    private boolean validateInput() {
        String password = passwordField.getText();
        String name = nameField.getText();
        Object a = isAdmin.getValue();

        if (password.length() > 3 && name.length() > 0 && a != null)
            return true;

        return false;
    }

    @FXML
    private void handleOkButtonAction(ActionEvent event) throws IOException {

        if (!validateName()){
            showNameAlert();
            return;
        }
        if (!validateInput()) {
            showAlert();
            return;
        }
        if(user == null)
            user = new User();
        user.setName(nameField.getText());
        user.setPassword(passwordField.getText());
        if(isAdmin.getValue().equals("TAK"))
            user.setIsAdmin(true);
        else
            user.setIsAdmin(false);

        userModule.addUser(user);
        exitAction(event);

    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Proszę uzupełnić wszystkie pola.\nHasło musi zawierać co najmniej 4 znaki.");
        alert.showAndWait();
    }

    private void showNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawna wartość");
        alert.setHeaderText("Niepoprawna wartość");
        alert.setContentText("Ta nazwa użytkownika znajduje się już w bazie danych.");
        alert.showAndWait();
    }

    @FXML
    private void initialize()
    {
        isAdmin.getItems().addAll("TAK", "NIE");
    }

}

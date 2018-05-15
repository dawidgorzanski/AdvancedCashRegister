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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @FXML
    private void initialize()
    {
        isAdmin.getItems().addAll("TAK", "NIE");
    }

}

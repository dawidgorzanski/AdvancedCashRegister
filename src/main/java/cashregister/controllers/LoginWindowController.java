package cashregister.controllers;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import cashregister.modules.ModulesManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

    public class LoginWindowController implements Initializable {

        @FXML
        TextField userIdTextField;

        @FXML
        PasswordField userPasswordField;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        @FXML
        private void handleCancelButtonAction(ActionEvent actionEvent) throws IOException{
            Stage primaryStage = cashregister.Main.getPrimaryStage();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/PrimaryWindow.fxml")));
            primaryStage.setScene(scene);
            primaryStage.setWidth(300);
            primaryStage.setHeight(300);
            primaryStage.show();
        }


        @FXML
        private void handleCheckLoginButtonAction(ActionEvent event) throws IOException {
            Alert alert;


            IUserDao dao = ModulesManager.getObjectByType(IUserDao.class);

            String userName = userIdTextField.getText();
            User user = null;
            for(User u : dao.getAll())
                if(u.getName().equals(userName)) {
                    user = u;
                    break;
                }



            if (user==null)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Kasjer o nazwie " + userName + " nie zarejestrowany");
                alert.show();
            }
            else
            {
                String goodPassword = user.getPassword();
                String possiblyPassword = userPasswordField.getText();
                if(goodPassword.equals(possiblyPassword))
                {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Logowanie udane");
                    alert.showAndWait();

                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml")));
                    Stage primaryStage = cashregister.Main.getPrimaryStage();
                    primaryStage.setScene(scene);
                    primaryStage.setMaximized(true);
                    primaryStage.show();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Hasła się nie zgadzają!");
                    alert.show();
                }

            }
        }
    }

    

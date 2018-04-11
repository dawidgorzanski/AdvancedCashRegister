package cashregister;

import cashregister.model.User;
import cashregister.barcode.BarcodeReader;
import cashregister.dao.interfaces.IUserDao;
import cashregister.modules.ModulesManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {
    public Main() {
        BarcodeReader.initializeBarcode("HUAWEIP9lite2017-Lineri");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setTitle("Kasa fiskalna");
        primaryStage.setMaximized(true);

        Scene scene = new Scene(root, 300, 275);
        /*scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(t.getCode()== KeyCode.DELETE)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błąd");
                    alert.setContentText("KLiknieto mnie");
                    alert.showAndWait();
                }
            }
        });*/

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent event) {
                                               BarcodeReader.uinitializeBarcode();
                                               //HibernateUtil.closeSessionFactory();
                                           }
                                       });

        primaryStage.show();
    }

    public static void main(String[] args) {
        ModulesManager.initialize();
        //IUserDao dao = ModulesManager.getObjectByType(IUserDao.class);
        //User user = dao.getById(1);
        //System.out.println(user.getName());
        launch(args);
    }
}

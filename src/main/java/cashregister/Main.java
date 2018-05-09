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

import javax.transaction.Transactional;
import java.util.List;

public class Main extends Application {
    public Main() {
        BarcodeReader.initializeBarcode("HUAWEIP9lite2017-Lineri");
    }

    @Override
    @Transactional
    public void start(Stage primaryStage) throws Exception{

        IUserDao dao = ModulesManager.getObjectByType(IUserDao.class);
        List<User> usersList = dao.getAll();
        if (usersList.size() == 0)
        {
            User admin = new User();
            admin.setId(1);
            admin.setName("admin");
            admin.setIsAdmin(true);
            admin.setPassword("admin");
            dao.save(admin);
        }

        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginWindow.fxml"));
        primaryStage.setTitle("Kasa fiskalna");
        Scene scene = new Scene (root,300,300);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                           @Override
                                           public void handle(WindowEvent event) {
                                               BarcodeReader.uinitializeBarcode();
                                           }
                                       });

        primaryStage.show();
    }

    public static void main(String[] args) {
        ModulesManager.initialize();
        launch(args);
    }

    private static Stage primaryStage;

    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }
}

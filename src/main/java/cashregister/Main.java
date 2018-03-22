package cashregister;

import cashregister.barcode.BarcodeReader;
import cashregister.dao.interfaces.IPersonDao;
import cashregister.model.Person;
import cashregister.modules.ModulesManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {
    public Main() {
        BarcodeReader.initializeBarcode("COM4");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setTitle("Kasa fiskalna");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 300, 275));

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
        /*IPersonDao dao = ModulesManager.getObjectByType(IPersonDao.class);
        Person person = dao.getById(1);
        System.out.println(person.getName());*/
        launch(args);
    }
}

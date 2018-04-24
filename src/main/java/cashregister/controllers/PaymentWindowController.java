package cashregister.controllers;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import cashregister.model.Receipt;
import cashregister.modules.ModulesManager;
import cashregister.modules.interfaces.IProductsListModule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentWindowController implements Initializable {

    public IProductsListModule getProductsListModule() {
        return productsListModule;
    }

    public void setProductsListModule(IProductsListModule productsListModule) {
        this.productsListModule = productsListModule;
    }

    private IProductsListModule productsListModule;

    @FXML
    private Label cashLabel, changeLabel, plnLabel1, plnLabel2;
    @FXML
    private TextField cashField, changeField;
    @FXML
    private Button confirmButton;

    @FXML
    private void handleCashButtonAction(ActionEvent event) {
        cashField.setVisible(true);
        changeField.setVisible(true);
        cashLabel.setVisible(true);
        changeLabel.setVisible(true);
        plnLabel1.setVisible(true);
        plnLabel2.setVisible(true);
        confirmButton.setVisible(true);
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cashField.setVisible(false);
        changeField.setVisible(false);
        cashLabel.setVisible(false);
        changeLabel.setVisible(false);
        plnLabel1.setVisible(false);
        plnLabel2.setVisible(false);
        confirmButton.setVisible(false);

    }

    @FXML
    @Transactional
    private void handleConfirmButtonAction(ActionEvent event) throws IOException
    {

        IReceiptDao receiptDao = ModulesManager.getObjectByType(IReceiptDao.class);
        ICustomerDao customerDao = ModulesManager.getObjectByType(ICustomerDao.class);


        Customer testCustomer = new Customer();
        testCustomer.setName("testowy");
        customerDao.saveOrUpdate(testCustomer);


        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales(productsListModule.getShoppingList());
        newReceipt.setCustomer(testCustomer);
        newReceipt.setDate(new Date());
        receiptDao.save(newReceipt);


        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();


        ObservableList<ProductForSale> tmp = productsListModule.getShoppingList();
        for(ProductForSale ps : tmp)
        {
            productsListModule.deleteProduct(ps);
        }





    }
}
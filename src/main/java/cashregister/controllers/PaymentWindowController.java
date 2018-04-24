package cashregister.controllers;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Customer;
import cashregister.model.ProductForSale;
import cashregister.model.Receipt;
import cashregister.modules.ModulesManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentWindowController implements Initializable {

    public List<ProductForSale> getProducts() {
        return products;
    }

    public void setProducts(List<ProductForSale> products) {
        this.products = products;
    }

    private List<ProductForSale> products;

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
    private void handleConfirmButtonAction(ActionEvent event)
    {
        for(ProductForSale ps : products)
        {
            System.out.println(ps.getName());
        }
        IReceiptDao receiptDao = ModulesManager.getObjectByType(IReceiptDao.class);
        ICustomerDao customerDao = ModulesManager.getObjectByType(ICustomerDao.class);

//
//        Customer testCustomer = new Customer();
//        testCustomer.setName("testowy");
//        customerDao.saveOrUpdate(testCustomer);


        Receipt newReceipt = new Receipt();
        newReceipt.setProductForSales(products);
        newReceipt.setCustomer(null);
        newReceipt.setDate(new Date());
        receiptDao.save(newReceipt);

    }
}
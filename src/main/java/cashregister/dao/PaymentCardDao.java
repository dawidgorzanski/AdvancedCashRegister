package cashregister.dao;

import cashregister.dao.interfaces.IPaymentCardDao;
import cashregister.model.PaymentCard;

public class PaymentCardDao extends BaseDao<PaymentCard> implements IPaymentCardDao {
    public PaymentCardDao(){super.setClass(PaymentCard.class);}

    @Override
    public void updateAccountBalance(PaymentCard paymentCard,double moneySpent) {
        double updatedAccountBalance = paymentCard.getAccountBalance() - moneySpent;
        paymentCard.setAccountBalance(updatedAccountBalance);
        saveOrUpdate(paymentCard);
    }
}

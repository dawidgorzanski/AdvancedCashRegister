package cashregister.dao.interfaces;

import cashregister.model.PaymentCard;

public interface IPaymentCardDao extends IBaseDao<PaymentCard> {
    void updateAccountBalance(PaymentCard paymentCard,double moneySpent);
}

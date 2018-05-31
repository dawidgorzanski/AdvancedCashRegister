package cashregister.helpers;

import cashregister.model.User;
import org.apache.commons.lang3.StringUtils;

public class ValidatorHelper {

    public static boolean validatePhoneNumber(String number) {
        if (StringUtils.isNumeric(number) && number.length() == 9)
            return true;

        if (number.length() == 0)
            return true;

        return false;
    }

    public static boolean validateClientBarcode(String barcode) {
        if (StringUtils.isNumeric(barcode) && barcode.length() == 15)
            return true;

        return false;
    }

    public static boolean validateNameNotEmpty(String name) {
        return name.length() > 0;
    }

    public static boolean validateMailAddress(String mailAddress) {
        return mailAddress.length() > 0 && mailAddress.contains("@");
    }

    public static boolean validateProductBarcodeLength(String barcode) {
        if (StringUtils.isNumeric(barcode) && barcode.length() == 13)
            return true;

        return false;
    }

    public static boolean validateQuantity(String quantity) {
        try {
            double result = Double.parseDouble(quantity);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validatePrice(String price) {
        try {
            double result = Double.parseDouble(price);
            if (result < 0)
                return false;

            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateIsNotNull(Object object) {
        return object != null;
    }

    public static boolean validateName(String name, User user, boolean edit, String oldName) {

        if (!edit && user == null)
            return true;
        if (edit && !name.equals(oldName) && user == null)
            return true;
        if (edit && name.equals(oldName))
            return true;

        return false;
    }

    public static boolean validateInput(String password, String name, Object isAdmin) {

        if (password.length() > 3 && name.length() > 0 && isAdmin != null)
            return true;

        return false;
    }
}

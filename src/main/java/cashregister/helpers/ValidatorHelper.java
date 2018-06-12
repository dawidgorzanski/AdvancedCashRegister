package cashregister.helpers;

import cashregister.model.User;
import org.apache.commons.lang3.StringUtils;

/**
 * Validates values given by user in GUI
 */
public class ValidatorHelper {

    /**
     * Checks if number given as parameter is numeric and if its length is 9
     * @param number
     * @return true if conditions are fulfilled, false otherwise
     */
    public static boolean validatePhoneNumber(String number) {
        if (StringUtils.isNumeric(number) && number.length() == 9)
            return true;

        if (number.length() == 0)
            return true;

        return false;
    }

    /**
     * Checks if barcode given as parameter is suitable for client
     * @param barcode
     * @return true if is suitable, false otherwise
     */
    public static boolean validateClientBarcode(String barcode) {
        if (StringUtils.isNumeric(barcode) && barcode.length() == 15)
            return true;

        return false;
    }

    /**
     * Checks if name given as parameter is not empty
     * @param name
     * @return true if is not empty, false otherwise
     */
    public static boolean validateNameNotEmpty(String name) {
        return name.length() > 0;
    }

    /**
     * Checks if email address given as parameter is valid
     * @param mailAddress
     * @return true if is valid, false otherwise
     */
    public static boolean validateMailAddress(String mailAddress) {
        return mailAddress.length() > 0 && mailAddress.contains("@");
    }

    /**
     * Checks if barcde given as parameter is suitable for ProductDefinition
     * @param barcode
     * @return true if is suitable, false otherwise
     */
    public static boolean validateProductBarcodeLength(String barcode) {
        if (StringUtils.isNumeric(barcode) && barcode.length() == 13)
            return true;

        return false;
    }

    /**
     * Checks if quantity given by parameter can be read as double
     * @param quantity
     * @return true if it can be read as double, false otherwise
     */
    public static boolean validateQuantity(String quantity) {
        try {
            double result = Double.parseDouble(quantity);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if price given by parameter is appropriate
     * @param price
     * @return true if is appropriate, false otherwise
     */
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

    /**
     * Checks if object given as parameter is null or not
     * @param object
     * @return true if is not, false otherwise
     */
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

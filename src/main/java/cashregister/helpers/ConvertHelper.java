package cashregister.helpers;

/**
 * Checks if String can be parsed to positive double number
 */
public class ConvertHelper {
    /**
     * checks if String passed as parameter can be parsed to positive double number
     * @param value
     * @return true if it can, false otherwise
     */
    public static boolean tryParsePositiveDouble(String value) {
        try {
            double result = Double.parseDouble(value);
            if (result < 0)
                return false;

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

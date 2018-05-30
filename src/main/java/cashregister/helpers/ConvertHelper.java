package cashregister.helpers;

public class ConvertHelper {
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

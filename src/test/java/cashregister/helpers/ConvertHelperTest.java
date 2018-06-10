package cashregister.helpers;

import org.junit.Test;

import static cashregister.helpers.ConvertHelper.tryParsePositiveDouble;
import static org.junit.Assert.*;

public class ConvertHelperTest {

    @Test
    public void tryParsePositiveDouble_ProperArgumentWithDot_ReturnTrue() {
        assertTrue(tryParsePositiveDouble("3.22"));
    }

    @Test
    public void tryParsePositiveDouble_ProperArgumentWithComa_ReturnFalse() {
        assertFalse(tryParsePositiveDouble("3,22"));
    }

    @Test
    public void tryParsePositiveDouble_BadArgument_ReturnFalse() {
        assertFalse(tryParsePositiveDouble("BadArgument"));
    }

    @Test
    public void tryParsePositiveDouble_PartlyProperArgument_ReturnFalse() {
        assertFalse(tryParsePositiveDouble("32.3BadArgument"));
    }

}
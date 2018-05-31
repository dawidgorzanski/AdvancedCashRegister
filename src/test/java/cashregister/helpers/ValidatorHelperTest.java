package cashregister.helpers;

import cashregister.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorHelperTest {

    @Test
    public void validatePhoneNumber_NumberNotNumeric_ReturnFalse() {
        assertFalse(ValidatorHelper.validatePhoneNumber("887228a78"));
    }

    @Test
    public void validatePhoneNumber_NumberLengthNot9Not0_ReturnFalse() {
        assertFalse(ValidatorHelper.validatePhoneNumber("12389238"));
    }

    @Test
    public void validatePhoneNumber_NumberLengthIs0_ReturnTrue() {
        assertTrue(ValidatorHelper.validatePhoneNumber(""));
    }

    @Test
    public void validatePhoneNumber_NumberIsNumericLengthIs9_ReturnTrue() {
        assertTrue(ValidatorHelper.validatePhoneNumber("849324221"));
    }

    @Test
    public void validateClientBarcode_BarcodeNotNumeric_ReturnFalse() {
        assertFalse(ValidatorHelper.validateClientBarcode("887228887228a78"));
    }

    @Test
    public void validateClientBarcode_NumberLengthNot15_ReturnFalse() {
        assertFalse(ValidatorHelper.validateClientBarcode("13238392383421"));
    }

    @Test
    public void validateClientBarcode_NumberIsNumericNumberLengthIs15_ReturnTrue() {
        assertTrue(ValidatorHelper.validateClientBarcode("132383923834281"));
    }

    @Test
    public void validateNameNotEmpty_NameEmpty_ReturnFalse() {
        assertFalse(ValidatorHelper.validateClientBarcode(""));
    }

    @Test
    public void validateNameNotEmpty_NameNotEmpty_ReturnTrue() {
        assertTrue(ValidatorHelper.validateNameNotEmpty("Name"));
    }

    @Test
    public void validateMailAddress_EmailEmpty_ReturnFalse() {
        assertFalse(ValidatorHelper.validateMailAddress(""));
    }

    @Test
    public void validateMailAddress_EmailWithoutSymbolAt_ReturnFalse() {
        assertFalse(ValidatorHelper.validateMailAddress("asdf.com"));
    }

    @Test
    public void validateMailAddress_EmailWithSymbolAt_ReturnTrue() {
        assertTrue(ValidatorHelper.validateMailAddress("asdf@sss.com"));
    }

    @Test
    public void validateProductBarcodeLength_BarcodeNotNumeric_ReturnFalse() {
        assertFalse(ValidatorHelper.validateProductBarcodeLength("8872288228a78"));
    }

    @Test
    public void validateProductBarcodeLength_NumberLengthNot13_ReturnFalse() {
        assertFalse(ValidatorHelper.validateProductBarcodeLength("132383383421"));
    }

    @Test
    public void validateProductBarcodeLength_NumberIsNumericNumberLengthIs13_ReturnTrue() {
        assertTrue(ValidatorHelper.validateProductBarcodeLength("1323839238342"));
    }

    @Test
    public void validateProductBarcodeLength_NameEmpty_ReturnFalse() {
        assertFalse(ValidatorHelper.validateProductBarcodeLength(""));
    }

    @Test
    public void validateQuantity_QuantityWithComa_ReturnFalse() {
        assertFalse( ValidatorHelper.validateQuantity("4934,2"));
    }

    @Test
    public void validateQuantity_QuantityWithDot_ReturnTrue() {
        assertTrue(ValidatorHelper.validateQuantity("43.23"));
    }

    @Test
    public void validateQuantity_QuantityIsInt_ReturnTrue() {
        assertTrue(ValidatorHelper.validateQuantity("4564"));
    }

    @Test
    public void validateQuantity_QuantityIsntNumber_ReturnFalse() {
        assertFalse(ValidatorHelper.validateQuantity("asd"));
    }

    @Test
    public void validateQuantity_QuantityIsPartlyNumber_ReturnFalse() {
        assertFalse(ValidatorHelper.validateQuantity("342.2ds"));
    }

    @Test
    public void validatePrice_PriceWithComa_ReturnFalse() {
        assertFalse( ValidatorHelper.validatePrice("1234,2"));
    }

    @Test
    public void validatePrice_PriceWithDot_ReturnTrue() {
        assertTrue(ValidatorHelper.validatePrice("6666.5"));
    }

    @Test
    public void validatePrice_PriceIsInt_ReturnTrue() {
        assertTrue(ValidatorHelper.validatePrice("5"));
    }

    @Test
    public void validatePrice_PriceIsntNumber_ReturnFalse() {
        assertFalse(ValidatorHelper.validatePrice("asdas"));
    }

    @Test
    public void validatePrice_PriceIsPartlyNumber_ReturnFalse() {
        assertFalse(ValidatorHelper.validatePrice("3342.2ds"));
    }

    @Test
    public void validatePrice_PriceIsLowerThan0_ReturnFalse() {
        assertFalse(ValidatorHelper.validatePrice("-20"));
    }

    @Test
    public void validateIsNotNull_ObjectIsNull_ReturnTrue() {
        assertFalse(ValidatorHelper.validateIsNotNull(null));
    }

    @Test
    public void validateIsNotNull_ObjectIsntNull_ReturnTrue() {
        assertTrue(ValidatorHelper.validateIsNotNull(new Object()));
    }

    @Test
    public void validateName_editIsFalseUserIsNull_ReturnTrue() {
        assertTrue(ValidatorHelper.validateName("name", null, false, "any"));
    }

    @Test
    public void validateName_editIsTrueNameEqualsOldName_ReturnTrue() {
        assertTrue(ValidatorHelper.validateName("sameName", null, true, "sameName"  ));
    }

    @Test
    public void validateName_editIsFalseUserNotNull_ReturnFalse() {
        User user = new User();
        assertFalse(ValidatorHelper.validateName("sameName", user, false, "sameName"  ));
    }

    @Test
    public void validateInput_PasswordShorterThan4_ReturnFalse() {
        assertFalse(ValidatorHelper.validateInput("123", "realName", new Object()  ));
    }

    @Test
    public void validateInput_NameIsEmpty_ReturnFalse() {
        assertFalse(ValidatorHelper.validateInput("12345678", "", new Object()  ));
    }

    @Test
    public void validateInput_PersonIsntAdmin_ReturnFalse() {
        assertFalse(ValidatorHelper.validateInput("12345678", "realName", null  ));
    }

    @Test
    public void validateInput_PasswordLongerThan3NameNotEmptyPersonIsAdmin_ReturnTrue() {
        assertTrue(ValidatorHelper.validateInput("12345678", "realName", new Object()  ));
    }




}
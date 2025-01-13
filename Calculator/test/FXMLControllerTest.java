
import fx1.FXMLController;

import javafx.embed.swing.JFXPanel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import org.junit.Before;

import org.junit.BeforeClass;
import org.junit.Test;

public class FXMLControllerTest {

    private FXMLController controller;

    @BeforeClass
    public static void initJFX() {
        new JFXPanel();
    }

    @Before
    public void setUp() {
        controller = new FXMLController();
        controller.textField = new javafx.scene.control.TextField();

    }

    @Test(expected = Exception.class)
    public void testOperatorAtStart() throws Exception {
        controller.appendOperator("+");
        controller.button_equals();
    }

    @Test
    public void testSingleNumberEquals() throws Exception {
        controller.appendNumber("7");
        controller.button_equals();
        assertEquals("7", controller.currentInput);
    }

    @Test(expected = Exception.class)
    public void testEmptyInputEquals() throws Exception {
        controller.button_equals();
    }

    @Test
    public void testAppendNumber() {
        controller.appendNumber("5");
        controller.appendNumber("3");
        assertEquals("53", controller.currentInput);
        assertEquals("53", controller.textField.getText());
    }

    @Test
    public void testAppendOperator() throws Exception {
        controller.appendNumber("8");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.calculateResult();
        assertEquals("10", controller.currentInput);
    }

    @Test
    public void testCalculateResult_Addition() throws Exception {
        controller.appendNumber("12");
        controller.appendOperator("+");
        controller.appendNumber("7");
        controller.calculateResult();
        assertEquals("19", controller.currentInput);
    }

    @Test
    public void testCalculateResult_NegativeSum() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("+");
        controller.appendNumber("10");
        controller.calculateResult();
        assertEquals("5", controller.currentInput);
    }

    @Test
    public void testCalculateResult_NegativeMul() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("*");
        controller.appendNumber("10");
        controller.calculateResult();
        assertEquals("-50", controller.currentInput);
    }
    @Test
    public void testCalculateResult_NegativeMulWithZero() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("*");
        controller.appendNumber("0");
        controller.calculateResult();
        assertEquals("0", controller.currentInput);
    }

    @Test
    public void testCalculateResult_NegativeDiv() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("/");
        controller.appendNumber("10");
        controller.calculateResult();
        assertEquals("-0.5", controller.currentInput);
    }

    @Test
    public void testCalculateResult_NegativeSub() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("-");
        controller.appendNumber("10");
        controller.calculateResult();
        assertEquals("-15", controller.currentInput);
    }

    @Test
    public void testEvaluateExpression_Complex() throws Exception {
        controller.appendNumber("-5");
        controller.appendOperator("+");
        controller.appendNumber("10");
        controller.appendOperator("-");
        controller.appendNumber("4");
        controller.appendOperator("*");
        controller.appendNumber("9");
        controller.appendOperator("/");
        controller.appendNumber("2");
        controller.calculateResult();
        assertEquals("4.5", controller.currentInput);
    }

    @Test
    public void testSequentialEquals() throws Exception {
        controller.appendNumber("10");
        controller.appendOperator("+");
        controller.appendNumber("5");
        controller.calculateResult();
        assertEquals("15", controller.currentInput);

        controller.button_equals();
        assertEquals("15", controller.currentInput);
    }

    @Test
    public void testClearAfterCalculation() throws Exception {
        controller.appendNumber("8");
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.calculateResult();
        assertEquals("16", controller.currentInput);

        controller.button_ac(null);
        assertEquals("", controller.currentInput);
        assertEquals("", controller.textField.getText());
    }

    @Test
    public void testLeadingZero() {
        controller.appendNumber("0");
        controller.appendNumber("5");
        assertEquals("05", controller.currentInput);
    }

    @Test
    public void testBackToBackOperations() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.appendNumber("4");
        controller.calculateResult();
        assertEquals("10", controller.currentInput);

        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.calculateResult();
        assertEquals("20", controller.currentInput);
    }

    @Test
    public void testNegativeResult() throws Exception {
        controller.appendNumber("5");
        controller.appendOperator("-");
        controller.appendNumber("10");
        controller.calculateResult();
        assertEquals("-5", controller.currentInput);
    }

    @Test
    public void testTrailingZeroRemoval() {
        double result = 7.0;
        assertEquals("7", controller.formatResult(result));
    }

    @Test
    public void testUndoOperatorInput() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.button_delete(null);
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.calculateResult();
        assertEquals("12", controller.currentInput);
    }

    @Test
    public void testCalculateResult_DecimalSum() throws Exception {
        controller.appendNumber("8.5");
        controller.appendOperator("+");
        controller.appendNumber("2.7");
        controller.calculateResult();
        assertEquals("11.2", controller.currentInput);
    }

    @Test
    public void testCalculateResult_DecimalSub() throws Exception {
        controller.appendNumber("8.5");
        controller.appendOperator("-");
        controller.appendNumber("2.7");
        controller.calculateResult();
        assertEquals("5.8", controller.currentInput);
    }

    @Test
    public void testCalculateResult_DecimalMul() throws Exception {
        controller.appendNumber("8.5");
        controller.appendOperator("*");
        controller.appendNumber("2.5");
        controller.calculateResult();
        assertEquals("21.25", controller.currentInput);
    }

    @Test
    public void testCalculateResult_DecimalDiv() throws Exception {
        controller.appendNumber("8.5");
        controller.appendOperator("/");
        controller.appendNumber("2.5");
        controller.calculateResult();
        assertEquals("3.4", controller.currentInput);
    }

    @Test
    public void testCalculateResult_Subtraction() throws Exception {
        controller.appendNumber("1");
        controller.appendNumber("5");
        controller.appendOperator("-");
        controller.appendNumber("5");
        controller.calculateResult();
        assertEquals("10", controller.currentInput);
    }

    @Test
    public void testCalculateResult_Multiplication() throws Exception {
        controller.appendNumber("3");
        controller.appendOperator("*");
        controller.appendNumber("7");
        controller.calculateResult();
        assertEquals("21", controller.currentInput);
    }

    @Test
    public void testCalculateResult_Division() throws Exception {
        controller.appendNumber("20");
        controller.appendOperator("/");
        controller.appendNumber("4");
        controller.calculateResult();
        assertEquals("5", controller.currentInput);
    }

    @Test(expected = ArithmeticException.class)
    public void testCalculateResult_DivisionByZero() throws Exception {
        controller.appendNumber("8");
        controller.appendOperator("/");
        controller.appendNumber("0");
        controller.calculateResult();
    }

    @Test
    public void testCalculateIntermediateResult() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.appendOperator("*");

        assertEquals("8.0*", controller.expression);
    }

    @Test
    public void testNegativeDecimalInput() throws Exception {
        controller.appendNumber("-");
        controller.appendNumber("2");
        controller.appendNumber(".");
        controller.appendNumber("5");
        controller.calculateResult();
        assertEquals("-2.5", controller.currentInput);
    }

    @Test
    public void testChangeDivToMul() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("/");
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("12", controller.currentInput);
    }

    @Test
    public void testChangeMulToDiv() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("*");
        controller.appendOperator("/");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("3", controller.currentInput);
    }

    @Test
    public void testChangeSubToSum() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("-");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("8", controller.currentInput);
    }

    @Test
    public void testChangeSumToSub() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.appendOperator("-");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("4", controller.currentInput);
    }

    @Test
    public void testChangeSubToSub() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("-");
        controller.appendOperator("-");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("4", controller.currentInput);
    }

    @Test
    public void testChangeSumToSum() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("8", controller.currentInput);
    }

    @Test
    public void testChangeSubToMul() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("-");
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("12", controller.currentInput);
    }

    @Test
    public void testChangeMulToSub() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("*");
        controller.appendOperator("-");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("4", controller.currentInput);
    }

    @Test
    public void testChangeMulToMul() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("*");
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("12", controller.currentInput);
    }

    @Test
    public void testChangeSubToDiv() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("-");
        controller.appendOperator("/");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("3", controller.currentInput);
    }

    @Test
    public void testChangeDivToSub() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("/");
        controller.appendOperator("-");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("4", controller.currentInput);
    }

    @Test
    public void testChangeDivToSum() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("/");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("8", controller.currentInput);
    }

    @Test
    public void testChangeMulToSum() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("*");
        controller.appendOperator("+");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("8", controller.currentInput);
    }

    @Test
    public void testChangeSumToMul() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("+");
        controller.appendOperator("*");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("12", controller.currentInput);
    }

    @Test
    public void testChangeDivToDiv() throws Exception {
        controller.appendNumber("6");
        controller.appendOperator("/");
        controller.appendOperator("/");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("3", controller.currentInput);
    }

    @Test
    public void testClear() {
        controller.appendNumber("123");
        controller.button_ac(null);
        assertEquals("", controller.currentInput);
    }

    @Test
    public void testDelete() {
        controller.appendNumber("456");
        controller.button_delete(null);
        assertEquals("45", controller.currentInput);
    }

    @Test
    public void testFormatResult_Integer() {
        double result = 10.0;
        assertEquals("10", controller.formatResult(result));
    }

    @Test
    public void testFormatResult_Decimal() {
        double result = 5.75;
        assertEquals("5.75", controller.formatResult(result));
    }

    @Test
    public void testFormatResult_Dot() throws Exception {
        controller.appendNumber("6");
        controller.appendNumber(".");
        controller.appendNumber(".");
        controller.appendNumber("2");
        controller.button_equals();
        assertEquals("6.2", controller.currentInput);
    }

    @Test
    public void testEvaluateExpression() throws Exception {
        String expression = "3+4*2";
        double result = controller.evaluateExpression(expression);
        assertEquals(11.0, result);
    }

    @Test
    public void testVeryLargeNumbers() throws Exception {
        controller.appendNumber("999999999");
        controller.appendOperator("*");
        controller.appendNumber("999999999");
        controller.calculateResult();

        assertTrue(Double.parseDouble(controller.currentInput) > 0);
    }

    @Test
    public void testVerySmallDecimals() throws Exception {
        controller.appendNumber("0.0000001");
        controller.appendOperator("*");
        controller.appendNumber("0.0000001");
        controller.calculateResult();
        assertTrue(Double.parseDouble(controller.currentInput) > 0);
    }

    @Test
    public void testMultipleDecimalPoints() {
        controller.appendNumber("1");
        controller.appendNumber(".");
        controller.appendNumber("2");
        controller.appendNumber(".");
        controller.appendNumber("3");
        assertEquals("1.23", controller.currentInput);
    }

    @Test
    public void testNewNumberAfterResult() throws Exception {
        controller.appendNumber("5");
        controller.appendOperator("+");
        controller.appendNumber("3");
        controller.calculateResult();
        controller.appendNumber("1");
        assertEquals("1", controller.currentInput);
    }

    @Test
    public void testContinueAfterDelete() throws Exception {
        controller.appendNumber("1");
        controller.appendNumber("2");
        controller.button_delete(null);
        controller.appendNumber("3");
        assertEquals("13", controller.currentInput);
    }

    @Test
    public void testEvaluateExpression_RecoveryAfterError() throws Exception {
        try {
            controller.appendNumber("8");
            controller.appendOperator("/");
            controller.appendNumber("0");
            controller.calculateResult();
            fail("Should have thrown an exception");
        } catch (Exception e) {
            controller.button_ac(null); 
            controller.appendNumber("1");
            controller.appendOperator("+");
            controller.appendNumber("2");
            controller.calculateResult();
            assertEquals("3", controller.currentInput);
        }
    }
    @Test
public void testVeryLargeMul() throws Exception {
    controller.appendNumber("999999999999999999");
    controller.appendOperator("*");
    controller.appendNumber("999999999999999999");
    controller.calculateResult();
    assertEquals("1.018E18", controller.currentInput);
}
@Test
public void testVerySmallMul() throws Exception {
    controller.appendNumber("1");
    controller.appendOperator("/");
    controller.appendNumber("999999999999999999");
    controller.calculateResult();
    assertEquals("1.0E-18", controller.currentInput);
}

    @Test
public void testMaximumDoubleMul() throws Exception {
    controller.appendNumber(String.valueOf(Double.MAX_VALUE));
    controller.appendOperator("*");
    controller.appendNumber(String.valueOf(Double.MAX_VALUE));
    controller.calculateResult();
    assertEquals("3.2317006071311", controller.currentInput);
}
@Test
public void testMaximumDoubleDiv() throws Exception {
    controller.appendNumber(String.valueOf(1));
    controller.appendOperator("/");
    controller.appendNumber(String.valueOf(Double.MAX_VALUE));
    controller.calculateResult();
    assertEquals("0.5562684646268004", controller.currentInput);
}
@Test
public void testMaximumDoubleSub() throws Exception {
    controller.appendNumber(String.valueOf(Double.MIN_VALUE));
    controller.appendOperator("-");
    controller.appendNumber(String.valueOf(Double.MAX_VALUE));
    controller.calculateResult();
    assertEquals("-320.8976931348623", controller.currentInput);
}




}

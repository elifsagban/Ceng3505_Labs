package com.example.mycalculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        MockCalculaterListener listener = new MockCalculaterListener();
        SimpleCalculator calc = new SimpleCalculator(listener);

        calc.setOperand(72);
        calc.setOperator("+");
        calc.setOperand(6);
        calc.setOperator("+");

        assertEquals(78,listener.result);

        calc.setOperator("-");
        calc.setOperand(3);

        assertEquals(75,listener.result);







    }
}
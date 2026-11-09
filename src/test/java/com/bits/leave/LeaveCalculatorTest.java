package com.bits.leave;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeaveCalculatorTest {

    LeaveCalculator calculator = new LeaveCalculator();

    // Test 1: Calculate remaining leave balance
    @Test
    public void testCalculateLeaveBalance() {

        int result = calculator.calculateLeaveBalance(20, 5);

        assertEquals(15, result);
    }

    // Test 2: Calculate leave encashment amount
    @Test
    public void testCalculateLeaveEncashment() {

        double result = calculator.calculateLeaveEncashment(5, 1000.0);

        assertEquals(5000.0, result, 0.001);
    }

    // Test 3: Calculate balance when no leaves are taken
    @Test
    public void testZeroLeavesTaken() {

        int result = calculator.calculateLeaveBalance(20, 0);

        assertEquals(20, result);
    }
}

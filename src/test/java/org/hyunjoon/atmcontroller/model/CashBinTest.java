package org.hyunjoon.atmcontroller.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CashBinTest {

    @Test
    public void testDeposit() {
        CashBin cashBin = new CashBin(10000);
        cashBin.deposit(2000);
        assertEquals(12000, cashBin.getCurrentCash());
    }
    
    @Test
    public void testWithdraw() {
        CashBin cashBin = new CashBin(100);

        try {
            cashBin.isEnough(200);
        } catch (RuntimeException e) {
            assertEquals("Not Enough Cash in Bin", e.getMessage());
        }

        try {
            cashBin.withdraw(200);
        } catch (RuntimeException e) {
            assertEquals("Not Enough Cash in Bin", e.getMessage());
        }
        
        assertEquals(100, cashBin.getCurrentCash());

        assertTrue(cashBin.isEnough(50));
        cashBin.withdraw(50);
        assertEquals(50, cashBin.getCurrentCash());
    }
}

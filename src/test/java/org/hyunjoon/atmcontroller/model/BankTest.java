package org.hyunjoon.atmcontroller.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BankTest {
    @Test
    public void testPinValidation() {
        Bank bank = createBank();
        assertTrue(bank.validatePin("0123456789"));
        assertFalse(bank.validatePin("0000000000"));
    }

    @Test
    public void testAccountListForPin() {
        Bank bank = createBank();
        assertEquals(3, bank.getAccountListForPin("0123456789").size());
        assertEquals(2, bank.getAccountListForPin("9876543210").size());
    }

    @Test
    public void testGetAccountBalance() {
        Bank bank = createBank();
        assertEquals(3000, bank.getAccountBalance(1));
    }

    @Test
    public void testUpdateAccountBalance() {
        Bank bank = createBank();
        try {
            bank.updateAccountBalance(0, -1000);
        } catch (RuntimeException e) {
            assertEquals("Not Enough Balance to Withdraw", e.getMessage());
        }
        assertEquals(0, bank.getAccountBalance(0));

        assertTrue(bank.updateAccountBalance(3, 1000));
        assertEquals(1800, bank.getAccountBalance(3));
    }

    private Bank createBank() {
        Map<String, CustomerInfo> pinToCustomer = new HashMap<>();

        AccountInfo accountA1 = new AccountInfo("customerA", "11111111111", 0);
        AccountInfo accountA2 = new AccountInfo("customerA", "33333333333", 2);
        AccountInfo accountA3 = new AccountInfo("customerA", "44444444444", 3);
        List<AccountInfo> accountListA = new ArrayList<>();
        accountListA.add(accountA1);
        accountListA.add(accountA2);
        accountListA.add(accountA3);
        CustomerInfo customerA = new CustomerInfo("customerA", accountListA);
        pinToCustomer.put("0123456789", customerA);

        AccountInfo accountB1 = new AccountInfo("customerB", "22222222222", 1);
        AccountInfo accountB2 = new AccountInfo("customerB", "55555555555", 4);
        List<AccountInfo> accountListB = new ArrayList<>();
        accountListB.add(accountB1);
        accountListB.add(accountB2);
        CustomerInfo customerB = new CustomerInfo("customerB", accountListB);
        pinToCustomer.put("9876543210", customerB);

        List<Integer> accountBalanceList = new ArrayList<>();
        accountBalanceList.add(0);
        accountBalanceList.add(3000);
        accountBalanceList.add(2300);
        accountBalanceList.add(800);
        accountBalanceList.add(10000);

        return new Bank(pinToCustomer, accountBalanceList);
    }
}

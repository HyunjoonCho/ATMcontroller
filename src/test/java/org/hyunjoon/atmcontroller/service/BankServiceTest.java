package org.hyunjoon.atmcontroller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyunjoon.atmcontroller.model.AccountInfo;
import org.hyunjoon.atmcontroller.model.Bank;
import org.hyunjoon.atmcontroller.model.CustomerInfo;
import org.junit.jupiter.api.Test;

public class BankServiceTest {
    static String PIN = "0123456789";

    @Test
    public void testGetTokenForPin() {
        BankService bankService = createBankService();
        assertThrows(RuntimeException.class, () -> bankService.getTokenForPin("0000000000"));
        assertNotNull(bankService.getTokenForPin(PIN));
    }

    @Test
    public void testMultipleAccessToPin() {
        BankService bankService = createBankService();

        Thread thread1 = new Thread(new Runnable() {
	        @Override
	        public void run() {
		        bankService.getTokenForPin(PIN);
	        }
        });

        Thread thread2 = new Thread(new Runnable() {
	        @Override
	        public void run() {
		        bankService.getTokenForPin(PIN);
	        }
        });

        try {
            thread1.start();
            thread2.start();
        } catch (RuntimeException e) {
            assertEquals("Token Already Issued for PIN", e.getMessage());
        }

    }

    @Test
    public void testGetAccountList() {
        BankService bankService = createBankService();
        
        try {
            bankService.getAccountList("token", "0000000000");
        } catch (RuntimeException e) {
            assertEquals("No Token Issued for PIN", e.getMessage());
        }

        String token = bankService.getTokenForPin(PIN);
        try {
            bankService.getAccountList("token", PIN);
        } catch (RuntimeException e) {
            assertEquals("Token Does Not Match PIN", e.getMessage());
        }

        assertEquals(3, bankService.getAccountList(token, PIN).size());
    }

    @Test
    public void testBalance() {
        BankService bankService = createBankService();
        String token = bankService.getTokenForPin(PIN);

        AccountInfo accountInfo = bankService.getAccountList(token, PIN).get(2);
        assertEquals(2300, bankService.getBalance(accountInfo));

        try {
            bankService.updateBalance(accountInfo, -2700);
        } catch (RuntimeException e) {
            assertEquals("Insufficient Balance", e.getMessage());
        }

        assertTrue(bankService.updateBalance(accountInfo, 500));
        assertEquals(2800, bankService.getBalance(accountInfo));
    }

    private BankService createBankService() {
        Map<String, CustomerInfo> pinToCustomer = new HashMap<>();

        AccountInfo account1 = new AccountInfo("customerA", "11111111111", 0);
        AccountInfo account2 = new AccountInfo("customerA", "22222222222", 1);
        AccountInfo account3 = new AccountInfo("customerA", "33333333333", 2);
        List<AccountInfo> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);
        CustomerInfo customerA = new CustomerInfo("customerA", accountList);
        pinToCustomer.put(PIN, customerA);

        List<Integer> accountBalanceList = new ArrayList<>();
        accountBalanceList.add(0);
        accountBalanceList.add(3000);
        accountBalanceList.add(2300);

        return new BankService(new Bank(pinToCustomer, accountBalanceList));
    }
}

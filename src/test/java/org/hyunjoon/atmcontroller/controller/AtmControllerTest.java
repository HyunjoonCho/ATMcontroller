package org.hyunjoon.atmcontroller.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyunjoon.atmcontroller.model.AccountInfo;
import org.hyunjoon.atmcontroller.model.Bank;
import org.hyunjoon.atmcontroller.model.CashBin;
import org.hyunjoon.atmcontroller.model.CustomerInfo;
import org.hyunjoon.atmcontroller.service.BankService;
import org.junit.jupiter.api.Test;

public class AtmControllerTest {

    static String PIN = "0123456789";

    @Test
    public void testPinValidation() {
        AtmController atmController = createAtmController();
        
        try {
            atmController.validatePIN("0000000000");
        } catch (RuntimeException e) {
            assertEquals("Invalid PIN", e.getMessage());
        }

        atmController.validatePIN(PIN);
        atmController.endSession();
    }

    @Test
    public void testGetAccountList() {
        AtmController atmController = createAtmController();
        atmController.validatePIN(PIN);
        assertEquals(3, atmController.getAccountList(PIN).size());
        atmController.endSession();
    }

    @Test
    public void testSeeBalance() {
        AtmController atmController = createAtmController();
        atmController.validatePIN(PIN);
        atmController.getAccountList(PIN);
        assertEquals(3000, atmController.seeBalance(1));
        atmController.endSession();
    }

    @Test
    public void testDeposit() {
        AtmController atmController = createAtmController();
        atmController.validatePIN(PIN);
        atmController.getAccountList(PIN);
        atmController.seeBalance(0);
        atmController.deposit(0, 2000);
        assertEquals(2000, atmController.seeBalance(0));
        atmController.endSession();
    }

    @Test
    public void testWithdraw() {
        AtmController atmController = createAtmController();
        atmController.validatePIN(PIN);
        atmController.getAccountList(PIN);
        atmController.seeBalance(1);
        atmController.withdraw(1, 4000);
        assertEquals(3000, atmController.seeBalance(1));
        atmController.withdraw(1, 3200);
        assertEquals(3000, atmController.seeBalance(1));
        atmController.withdraw(1, 1500);
        assertEquals(1500, atmController.seeBalance(1));
        atmController.endSession();
    }


    private AtmController createAtmController() {
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

        BankService bankService = new BankService(new Bank(pinToCustomer, accountBalanceList));

        return new AtmController(bankService, new CashBin(3500));
    }
}

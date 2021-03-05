package org.hyunjoon.atmcontroller.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Repository;

@Repository
public class Bank {
    private final Map<String, CustomerInfo> pinToCustomer;
    private final List<Integer> accountBalanceList;

    public Bank() {
        pinToCustomer = new HashMap<>();
        accountBalanceList = new ArrayList<>();

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

        accountBalanceList.add(0);
        accountBalanceList.add(3000);
        accountBalanceList.add(2300);
        accountBalanceList.add(800);
        accountBalanceList.add(10000);
    }

    public Bank(Map<String, CustomerInfo> pinToCustomer, List<Integer> accountBalanceList) {
        this.pinToCustomer = Objects.requireNonNull(pinToCustomer, "pinToCustomer");
        this.accountBalanceList = Objects.requireNonNull(accountBalanceList, "accountBalanceList");
    }

    public boolean validatePin(String pin) {
        return pinToCustomer.get(pin) != null;
    }

    public List<AccountInfo> getAccountListForPin(String pin) {
        CustomerInfo customerInfo = pinToCustomer.get(pin);
        if (customerInfo == null) {
            return null;
        } else {
            return customerInfo.getAccountList();
        }
    }

    public int getAccountBalance(int accountId) {
        return accountBalanceList.get(accountId);
    }

    public boolean updateAccountBalance(int accountId, int updateAmount) {
        int updatedBalance = accountBalanceList.get(accountId) + updateAmount;
        if (updatedBalance < 0) {
            throw new RuntimeException("Insufficient Balance");
        } else {
            accountBalanceList.set(accountId, updatedBalance);
            return true;
        }
    }
}

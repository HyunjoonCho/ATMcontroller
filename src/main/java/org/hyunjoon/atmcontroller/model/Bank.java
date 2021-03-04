package org.hyunjoon.atmcontroller.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Repository;

@Repository
public class Bank {
    private final Map<String, CustomerInfo> pinToCustomer;
    private final List<Integer> accountBalanceList;

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
            return false;
        } else {
            accountBalanceList.set(accountId, updatedBalance);
            return true;
        }
    }
}

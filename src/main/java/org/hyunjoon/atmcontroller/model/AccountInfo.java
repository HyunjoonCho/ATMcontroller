package org.hyunjoon.atmcontroller.model;

import java.util.Objects;

public class AccountInfo {
    private final String accountHolder;
    private final String accountNumber;
    private final int accountId;

    public AccountInfo(String accountHolder, String accountNumber, int accountId) {
        this.accountHolder = Objects.requireNonNull(accountHolder, "accountHolder");
        this.accountNumber = Objects.requireNonNull(accountNumber, "accountNumber");
        this.accountId = accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountName() {
        return accountNumber;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccountInfo {");
        sb.append("AccountHoler: ").append(accountHolder);
        sb.append(", AccountNumber: ").append(accountNumber);
        sb.append("}");

        return sb.toString();
    }
}

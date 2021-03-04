package org.hyunjoon.atmcontroller.model;

import java.util.List;
import java.util.Objects;

public class CustomerInfo {
    private final String name; 
    private final List<AccountInfo> accountList;

    public CustomerInfo(String name, List<AccountInfo> accountList) {
        this.name = Objects.requireNonNull(name, "name");
        this.accountList = Objects.requireNonNull(accountList, "accountList");
    }

    public String getName() {
        return name;
    }

    public List<AccountInfo> getAccountList() {
        return accountList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomerInfo{");
        sb.append("name: ").append(name);
        sb.append(", accountList: ").append(accountList);
        sb.append("}");

        return sb.toString();
    }
}

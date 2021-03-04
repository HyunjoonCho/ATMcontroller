package org.hyunjoon.atmcontroller.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.hyunjoon.atmcontroller.model.AccountInfo;
import org.hyunjoon.atmcontroller.model.Bank;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final Bank bank;
    private final List<String> tokenList;
    
    public BankService(Bank bank) {
        this.bank = Objects.requireNonNull(bank, "bank");
        tokenList = new ArrayList<>();
    }

    public String getTokenForPin(String pin) throws RuntimeException {
        if (bank.validatePin(pin)) {
            return generateToken();
        } else {
            throw new RuntimeException("Invalid PIN");
        }
    }

    private String generateToken() {
        String token;

        do {
            byte[] array = new byte[10];
            new Random().nextBytes(array);
            token = new String(array, Charset.forName("UTF-8"));
        } while (!tokenList.contains(token));

        tokenList.add(token);

        return token;
    }

    public List<AccountInfo> getAccountList(String token, String pin) throws RuntimeException {
        if (!tokenList.contains(token)) {
            throw new RuntimeException("Invalid Token");
        }

        List<AccountInfo> accountList = bank.getAccountListForPin(pin);
        if (accountList == null) {
            throw new RuntimeException("Invalid PIN");
        }

        return accountList; 
    }

    public int getBalance(AccountInfo accountInfo) {
        return bank.getAccountBalance(accountInfo.getAccountId());
    } 

    public boolean updateBalance(AccountInfo accountInfo, int updatedBalance) {
        return bank.updateAccountBalance(accountInfo.getAccountId(), updatedBalance);
    }
}

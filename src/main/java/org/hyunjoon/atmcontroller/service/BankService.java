package org.hyunjoon.atmcontroller.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.hyunjoon.atmcontroller.model.AccountInfo;
import org.hyunjoon.atmcontroller.model.Bank;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final Bank bank;
    private final Map<String, String> pinToToken;
    private final Map<String, String> tokenToPin;
    
    public BankService(Bank bank) {
        this.bank = Objects.requireNonNull(bank, "bank");
        pinToToken = new HashMap<>();
        tokenToPin = new HashMap<>();
    }

    public String getTokenForPin(String pin) throws RuntimeException {
        if (bank.validatePin(pin)) {
            if (pinToToken.containsKey(pin)) {
                throw new RuntimeException("Token Already Issued for PIN");
            }
            return generateToken(pin);
        } else {
            throw new RuntimeException("Invalid PIN");
        }
    }

    private String generateToken(String pin) {
        String token;

        do {
            byte[] array = new byte[10];
            new Random().nextBytes(array);
            token = new String(array, Charset.forName("UTF-8"));
        } while (pinToToken.containsValue(token));

        tokenToPin.put(token, pin);
        pinToToken.put(pin, token);

        return token;
    }

    public List<AccountInfo> getAccountList(String token, String pin) throws RuntimeException {
        if (!pinToToken.containsKey(pin)) {
            throw new RuntimeException("No Token Issued for PIN");
        }

        if (!token.equals(pinToToken.get(pin))) {
            throw new RuntimeException("Token Does Not Match PIN");
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

    public boolean updateBalance(AccountInfo accountInfo, int amount) {
        return bank.updateAccountBalance(accountInfo.getAccountId(), amount);
    }

    public void removeToken(String token) {
        if (pinToToken.containsValue(token)) {
            pinToToken.remove(tokenToPin.get(token));
            tokenToPin.remove(token);
        }
    }
}

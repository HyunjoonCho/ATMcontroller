package org.hyunjoon.atmcontroller.controller;

import java.util.List;
import java.util.Objects;

import org.hyunjoon.atmcontroller.model.AccountInfo;
import org.hyunjoon.atmcontroller.service.BankService;
import org.hyunjoon.atmcontroller.model.CashBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/atmController")
public class AtmController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BankService bankService;
    private final CashBin cashBin;

    private String token; 
    private List<AccountInfo> accountList;

    public AtmController(BankService bankService, CashBin cashBin) {
        this.bankService = Objects.requireNonNull(bankService, "bankService");
        this.cashBin = Objects.requireNonNull(cashBin, "cashBin");
    }

    @RequestMapping("/validatePIN")
    public void validatePIN(@RequestParam("pin") String pin) {
        logger.info("Validating PIN...");
        try {
            token = bankService.getTokenForPin(pin);
            logger.info("Valid PIN");
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
        }
    }

    @RequestMapping("/accountList")
    @ResponseBody
    public List<AccountInfo> getAccountList(@RequestParam("pin") String pin) {        
        logger.info("Getting Account List...");

        if (token == null) {
            logger.warn("No Token Issued");
            return null;
        }

        accountList = bankService.getAccountList(token, pin);
        logger.info("Your Accounts: {}", accountList);

        return accountList;
    }

    @RequestMapping("/balance")
    @ResponseBody
    public int seeBalance(@RequestParam("index") int index) {
        logger.info("Getting Balance for Account {}...", index + 1);

        try {
            isValid(index);
            int balance = bankService.getBalance(accountList.get(index));
            logger.info("Your Balance: {}", balance);
            return balance;
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            return -1;
        }

    }

    @RequestMapping("/deposit")
    public void deposit(
            @RequestParam("index") int index,
            @RequestParam("amount") int amount) {
        logger.info("Depositting to Account {}...", index + 1);

        try {
            isValid(index);
            bankService.updateBalance(accountList.get(index), amount); 
            cashBin.deposit(amount);
            logger.info("Deposit Succeeded");
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
        }
    }

    @RequestMapping("/withdraw")
    public void withdraw(            
            @RequestParam("index") int index,
            @RequestParam("amount") int amount) {
        logger.info("Withdrawing from Account {}...", index + 1);

        try {
            isValid(index);
            cashBin.isEnough(amount); 
            bankService.updateBalance(accountList.get(index), amount * -1);
            cashBin.withdraw(amount);
            logger.info("Withdraw Succeeded");
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
        }
    }

    private void isValid(int index) {
        if (accountList == null) {
            throw new RuntimeException("No Account List");
        }

        if (index < 0 || index >= accountList.size()) {
            throw new RuntimeException("No Such Account " + (index + 1));
        }
    }

    @RequestMapping("/endSession")
    public void endSession() {
        if (token != null) {
            bankService.removeToken(token);
            token = null;
        }
        accountList = null;
        logger.info("Session Ended");
    }
}

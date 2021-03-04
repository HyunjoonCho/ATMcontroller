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
        token = bankService.getTokenForPin(pin);
    }

    @RequestMapping("/getAccountList")
    public List<AccountInfo> getAccountList(@RequestParam("pin") String pin) {        
        logger.info("Getting Account List...");
        accountList = bankService.getAccountList(token, pin);

        return accountList;
    }

    @RequestMapping("/balance")
    public int seeBalance(@RequestParam("index") int index) {
        logger.info("Getting Balance...");

        return bankService.getBalance(accountList.get(index));
    }

    @RequestMapping("/deposit")
    public void deposit(
            @RequestParam("index") int index,
            @RequestParam("amount") int amount) {
        if (bankService.updateBalance(accountList.get(index), amount)) {
            cashBin.deposit(amount);
        }
    }

    @RequestMapping("/withdraw")
    public void withdraw(            
            @RequestParam("index") int index,
            @RequestParam("amount") int amount) {
        if (bankService.updateBalance(accountList.get(index), amount)) {
            cashBin.withdraw(amount);
        }
    }

    @RequestMapping("/endSession")
    public void endSession() {
        token = null;
        accountList = null;
    }
}

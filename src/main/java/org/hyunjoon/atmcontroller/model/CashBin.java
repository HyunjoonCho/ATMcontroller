package org.hyunjoon.atmcontroller.model;

import org.springframework.stereotype.Repository;

@Repository
public class CashBin {
    private int currentCash;

    public CashBin(int currentCash) {
        this.currentCash = currentCash;
    }

    public void deposit(int depositAmount) {
        currentCash += depositAmount;
    }

    public boolean withdraw(int withdrawAmount) {
        if (currentCash < withdrawAmount) {
            return false;
        } else {
            currentCash -= withdrawAmount;
            return true;
        }
    }
}

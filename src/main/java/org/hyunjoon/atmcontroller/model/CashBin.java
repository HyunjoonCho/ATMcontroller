package org.hyunjoon.atmcontroller.model;

import org.springframework.stereotype.Repository;

@Repository
public class CashBin {
    private int currentCash;

    public CashBin() {
        this.currentCash = 6000;
    }

    public CashBin(int currentCash) {
        this.currentCash = currentCash;
    }

    public int getCurrentCash() {
        return currentCash;
    }

    public void deposit(int depositAmount) {
        currentCash += depositAmount;
    }

    public boolean isEnough(int withdrawAmount) {
        if (currentCash < withdrawAmount) {
            throw new RuntimeException("Not Enough Cash in Bin");
        }

        return true;
    }

    public void withdraw(int withdrawAmount) {
        if (isEnough(withdrawAmount)) {
            currentCash -= withdrawAmount;
        }
    }
}

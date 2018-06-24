package com.progressoft.impl;

import com.progressoft.induction.tp.Transaction;
import com.progressoft.induction.tp.TransactionProcessor;
import com.progressoft.induction.tp.Violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransactionProcessor implements TransactionProcessor {

    protected List<Transaction> transactionsList = new ArrayList<>();
    protected List<Violation> violationsList = new ArrayList<>();

    @Override
    public List<Transaction> getImportedTransactions() {
        return transactionsList;
    }

    @Override
    public List<Violation> validate() {
        return violationsList;
    }

    @Override
    public boolean isBalanced() {
        BigDecimal credit = new BigDecimal(0);
        BigDecimal debit = new BigDecimal(0);
        for (Transaction item : transactionsList) {
            if (item.getType().equals("C")) {
                credit = credit.add(item.getAmount());
            } else if (item.getType().equals("D")) {
                debit = debit.add(item.getAmount());
            }
        }
        return credit.equals(debit);
    }


    protected void checkType(String type, int order) {
        if (!(type.equals("D") || type.equals("C") || type.equals(null))) {
            violationsList.add(new Violation(order, "type", "Type Must Be C or D"));
        }
    }

    protected BigDecimal checkAmount(String amountParam, int order) {
        BigDecimal amount = new BigDecimal(0);
        try {
            amount = new BigDecimal(amountParam);
            if (amount.equals(new BigDecimal(0))) {
                violationsList.add(new Violation(order, "amount", "Amount Must Be Greater Than Zero"));
            }
        } catch (NumberFormatException nfe) {
            violationsList.add(new Violation(order, "amount", "Amount Must Be Number"));
        }
        return amount;
    }

    protected void checkNarration(String narration, int order) {
        if (narration.equals(null)) {
            violationsList.add(new Violation(order, "narration", "Narration/Decription Not Found"));
        }
    }
}

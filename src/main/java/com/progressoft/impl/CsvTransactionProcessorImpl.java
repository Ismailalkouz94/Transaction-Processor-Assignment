package com.progressoft.impl;

import com.progressoft.induction.tp.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class CsvTransactionProcessorImpl extends BaseTransactionProcessor {

    @Override
    public void importTransactions(InputStream is) {
        String line;
        String cvsSplitBy = ",";

        String type;
        BigDecimal amount;
        String narration;

        Transaction transactionObj;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

            int order = 1;
            while ((line = bufferedReader.readLine()) != null) {

                String[] transactionsArr = line.split(cvsSplitBy);

                //for "type" intialize and validation
                type = transactionsArr[0];
                checkType(type, order);

                //for "amount" intialize and validation
                amount = checkAmount(transactionsArr[1], order);

                //for "narration" intialize and validation
                narration = transactionsArr[2];
                checkNarration(narration, order);

                transactionObj = new Transaction(type, amount, narration);
                transactionsList.add(transactionObj);
                order++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

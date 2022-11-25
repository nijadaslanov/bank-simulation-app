package com.simulation.service;

import com.simulation.model.Account;
import com.simulation.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message);

    List<Transaction> findAllTransaction();


    List<Transaction>findTransactionsList();
}




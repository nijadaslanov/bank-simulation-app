package com.simulation.service.impl;

import com.simulation.exception.BadRequestException;
import com.simulation.model.Account;
import com.simulation.model.Transaction;
import com.simulation.repository.AccountRepository;
import com.simulation.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        validateAccount(sender, receiver);


        return null;
    }

    private void validateAccount(Account sender, Account receiver) {

       /*
       -if any of the accounts is null
       -if account ids are the same(same account)
       -if account exist in the database(repository)
        */


        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different than receiver");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());


    }

    private Account findAccountById(UUID id) {
        return accountRepository.findById(id);

    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;

    }
}

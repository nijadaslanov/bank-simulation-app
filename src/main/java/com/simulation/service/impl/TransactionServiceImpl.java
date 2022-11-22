package com.simulation.service.impl;

import com.simulation.enums.AccountType;
import com.simulation.exception.AccountOwnershipException;
import com.simulation.exception.BadRequestException;
import com.simulation.exception.BalanceNotSufficientException;
import com.simulation.exception.UnderConstructionException;
import com.simulation.model.Account;
import com.simulation.model.Transaction;
import com.simulation.repository.AccountRepository;
import com.simulation.repository.TransactionRepository;
import com.simulation.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnerShip(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

         /*
         after all validations completed, and money is transferred, we need to create Transaction object
         save and return it
         */
            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId()).receiver(receiver.getId())
                    .creationDate(creationDate).message(message).build();

            return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {

        if (checkSenderBalance(sender, amount)) {
            //make transaction
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        } else {
            // not enough balance
            throw new BalanceNotSufficientException("Your balance is not enough for this transfer");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {

        // verify that sender has enough balance

        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;


    }

    private void checkAccountOwnerShip(Account sender, Account receiver) {

        /*
          - write an if statement that checks if one of the account is saving,
            and user if of sender or receiver is not the same,
            throw AccountOwnershipException
          - if one account is saving and ids not match throw exception
         */

        if ((sender.getAccountType().equals(AccountType.SAVING)
                || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("One of the accounts is Saving." +
                    " Transactions between savings and checking accounts are allowed between same user accounts only. " +
                    " User id's don't match ");
        }


    }

    private void validateAccount(Account sender, Account receiver) {

       /*
       -if any of the accounts is null
       -if account id_s are the same(same account)
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

        return transactionRepository.findAll();

    }
}

package com.simulation.service.impl;

import com.simulation.enums.AccountStatus;
import com.simulation.enums.AccountType;
import com.simulation.model.Account;
import com.simulation.repository.AccountRepository;
import com.simulation.service.AccountService;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
public class AccountServiceImpl implements AccountService {


    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {

        Account account = Account.builder().id(UUID.randomUUID())
                .userId(userId).accountType(accountType).balance(balance)
                .creationDate(creationDate).accountStatus(AccountStatus.ACTIVE).build();
        return accountRepository.save(account);


    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }
}

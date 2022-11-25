package com.simulation.service;

import com.simulation.enums.AccountType;
import com.simulation.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<Account> listAllAccount();


    void deleteAccount(UUID id);
}

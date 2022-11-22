package com.simulation;

import com.simulation.enums.AccountType;
import com.simulation.model.Account;
import com.simulation.service.AccountService;
import com.simulation.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

        // get account and transaction service beans

        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        // create 2 account sender and receiver

        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.SAVING, 1L);

        accountService.listAllAccount().forEach(System.out::println);
        transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1");
        System.out.println(transactionService.findAllTransaction().get(0));


    }

}

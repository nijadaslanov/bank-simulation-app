package com.simulation.controller;


import com.simulation.model.Transaction;
import com.simulation.service.AccountService;
import com.simulation.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/make-transfer")
    public String makeTransfer(Model model) {

        // we need all accounts to provide them as sender, receiver
        model.addAttribute("accounts", accountService.listAllAccount());
        // we need empty transaction object to get info from UI
        model.addAttribute("transaction", Transaction.builder().build());
        // we need list of last 10 transactions
        model.addAttribute("lastTransactions",transactionService.findTransactionsList());


        return "transaction/make-transfer";
    }


}

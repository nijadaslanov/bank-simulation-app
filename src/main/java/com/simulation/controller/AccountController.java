package com.simulation.controller;

import com.simulation.service.impl.AccountServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {

        model.addAttribute("accountList", accountService.listAllAccount());

        return "account/index";
    }


}

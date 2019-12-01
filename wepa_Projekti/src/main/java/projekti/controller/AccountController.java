/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.model.Account;
import projekti.model.AccountRepository;

/**
 *
 * @author ptuomola
 */

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository ar;
    
    @GetMapping("/accounts/{urlSuffix}")
    public String getAccountPage(@PathVariable String urlSuffix, Model model)
    {
        Account account = ar.findByUrlStringIgnoreCase(urlSuffix);
        if(account == null) return "redirect:/";
        
        model.addAttribute("account", account);
        return "account";
    }
    
    @GetMapping("/accounts")
    public String getAccountSearch()
    {
        return "accountSearch";
    }
    
    @PostMapping("/accounts")
    public String doAccountSearch(@RequestParam String searchString, Model model)
    {
        model.addAttribute("searchResult", ar.searchAccounts(searchString));
        return "accountSearch";
    }
}

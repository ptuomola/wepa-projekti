/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

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
import projekti.model.Account;
import projekti.model.AccountRepository;

/**
 *
 * @author ptuomola
 */

@Controller
public class RegistrationController {
    
    @Autowired
    private AccountRepository ar;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute Account accont)
    {
        return "register";
    }
    
    @PostMapping("/register")
    public String postRegisterPage(@Valid @ModelAttribute Account account, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "register";
        }
        
        String password = account.getPassword();

        // Password validation rules
        if(password != null)
        {
            if(password.length() < 8)
            {
                bindingResult.rejectValue("password", "error.password", "Password must be at least 8 characters long");
            }
        
            if(!password.matches(".*[0-9].*"))
            {
                bindingResult.rejectValue("password", "error.password", "Password must contain at least one number");
            }

            if(!password.matches(".*[a-z].*"))
            {
                bindingResult.rejectValue("password", "error.password", "Password must contain at least one lowercase letter");
            }

            if(!password.matches(".*[A-Z].*"))
            {
                bindingResult.rejectValue("password", "error.password", "Password must contain at least one uppercase letter");
            }
        }
        
        if(!account.getPasswordConfirm().equals(account.getPassword()))
        {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "The passwords do not match");
        }
        
        if(ar.findByUsernameIgnoreCase(account.getUsername()) != null)
        {
            bindingResult.rejectValue("username", "error.username", "The selected username is already in use");
        }
        
        if(ar.findByUrlStringIgnoreCase(account.getUrlString()) != null)
        {
            bindingResult.rejectValue("urlString", "error.urlString", "The selected URL suffix is already in use");
        }
        
        if(bindingResult.hasErrors())
        {
            return "register";
        }

        
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        ar.save(account);
        
        return "redirect:/login?newUser=true";
    }
}

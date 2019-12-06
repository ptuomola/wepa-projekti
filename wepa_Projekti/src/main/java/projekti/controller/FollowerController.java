/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import projekti.model.FollowerRepository;

/**
 *
 * @author ptuomola
 */

@Controller
public class FollowerController {
    
    @Autowired
    private FollowerRepository fr;
    
    @Autowired
    private AccountRepository ar;
    
    @GetMapping("/followers")
    public String getFollowers(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Account owner = ar.findByUsernameIgnoreCase(username);        
        model.addAttribute("followers", fr.findByFollowedAccount(owner));
        model.addAttribute("following", fr.findByFollowingAccount(owner));
        return "followers";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import projekti.model.Follower;
import projekti.model.FollowerRepository;
import projekti.model.MessageRepository;

/**
 *
 * @author ptuomola
 */

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository ar;
    
    @Autowired
    private FollowerRepository fr;
    
    @Autowired
    private MessageRepository mr;
    
    @GetMapping("/")
    public String mainPage()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth != null)
        {
            String username = auth.getName();        
            Account user = ar.findByUsernameIgnoreCase(username);
            
            if(user != null)
            {
                return "redirect:/accounts/" + user.getUrlString();
            }
        }
        
        return "index";
    }
    
    @GetMapping("/accounts/{urlSuffix}")
    public String getAccountPage(@PathVariable String urlSuffix, Model model)
    {
        Account account = ar.findByUrlStringIgnoreCase(urlSuffix);
        if(account == null) return "redirect:/";
        
        model.addAttribute("account", account);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth != null)
        {
            String username = auth.getName();        
            Account user = ar.findByUsernameIgnoreCase(username);
            
            model.addAttribute("follower", fr.getByFollowingAccountAndFollowedAccount(user, account));
            
            if(user == account)
            {
                model.addAttribute("myHomePage", true);
            }
        }
        
        model.addAttribute("messages", mr.getMessagesForDisplay(account));
        
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
    
    @PostMapping("/accounts/{id}/toggleFollowing")
    public String toggleFollowing(@PathVariable Long id, Model model, HttpServletRequest request)
    {
        Account followedAccount = ar.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null || followedAccount == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        String username = auth.getName();        
        Account followingAccount = ar.findByUsernameIgnoreCase(username);
        
        if(followingAccount == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        Follower follower = fr.getByFollowingAccountAndFollowedAccount(followingAccount, followedAccount);

        if(follower != null)
        {
            fr.delete(follower);
            follower = null;
        }
        else
        {
            follower = new Follower();
            follower.setFollowedAccount(followedAccount);
            follower.setFollowingAccount(followingAccount);
            follower.setCreatedOn(new Date());
            fr.save(follower);
        }
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}

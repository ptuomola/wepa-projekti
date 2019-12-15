/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.model.Account;
import projekti.model.AccountRepository;
import projekti.model.FollowerRepository;
import projekti.model.Image;
import projekti.model.ImageRepository;
import projekti.model.MessageRepository;
import projekti.services.AccountService;
import projekti.services.BlockService;
import projekti.services.FollowerService;

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
    
    @Autowired
    private ImageRepository ir;
    
    @Autowired
    private AccountService as;
    
    @Autowired 
    private FollowerService fs;
    
    @Autowired 
    private BlockService bs;
    
    @GetMapping("/")
    public String mainPage()
    {
        Account user = as.getLoggedInAccount();

        if(user != null)
        {
            return "redirect:/accounts/" + user.getUrlString();
        }
        
        return "index";
    }
    
    @Transactional
    @GetMapping("/accounts/{urlSuffix}")
    public String getAccountPage(@PathVariable String urlSuffix, Model model)
    {
        Account account = ar.findByUrlStringIgnoreCase(urlSuffix);
        if(account == null) return "redirect:/";
        
        model.addAttribute("account", account);
        
        Account user = as.getLoggedInAccount();
        model.addAttribute("loggedInUser", user);
        
        if(user != null)
        {
            model.addAttribute("follower", fr.getByFollowingAccountAndFollowedAccount(user, account));
            model.addAttribute("hasBlocked", bs.isBlocked(account, user));
            model.addAttribute("haveBlocked", bs.isBlocked(user, account));
        }
            
        if(user == account)
        {
            model.addAttribute("myHomePage", true);
        }
        
        model.addAttribute("numFollowers", fr.countByFollowedAccount(account));        
        model.addAttribute("numFollowing", fr.countByFollowingAccount(account));
        model.addAttribute("numImages", ir.countByOwner(account));
        
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
        Account followingAccount = as.getLoggedInAccount();
        
        if(followingAccount == null || followedAccount == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        fs.toggleFollowing(followingAccount, followedAccount);
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @PostMapping("/accounts/{id}/toggleBlock")
    public String toggleBlock(@PathVariable Long id, Model model, HttpServletRequest request)
    {
        Account blockedAccount = ar.findById(id).get();
        Account blockingAccount = as.getLoggedInAccount();
        
        if(blockedAccount == null || blockingAccount == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        bs.toggleBlock(blockedAccount, blockingAccount);
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @GetMapping("/accounts/{id}/images")
    public String getImages(@PathVariable Long id, Model model)
    {
        Account owner = ar.getOne(id);
        List<Image> images = ir.findByOwnerOrderByIdAsc(owner);
        model.addAttribute("count", images.size());
        model.addAttribute("images", images);
        model.addAttribute("owner", owner);

        Account user = as.getLoggedInAccount();
        model.addAttribute("loggedInUser", user);
        model.addAttribute("profileImage", user.getProfileImage());
        
        if(user != null)
        {
            model.addAttribute("follower", fr.getByFollowingAccountAndFollowedAccount(user, owner));
        }
        
        return "images";
    }
}

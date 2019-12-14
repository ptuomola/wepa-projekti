/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.model.AccountRepository;

/**
 *
 * @author ptuomola
 */

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository ar;
    
    public Account getLoggedInAccount()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null)
        {
            return null;
        }
        
        String username = auth.getName();        
        Account loggedInAccount = ar.findByUsernameIgnoreCase(username);
        
        return loggedInAccount;
    }    
}

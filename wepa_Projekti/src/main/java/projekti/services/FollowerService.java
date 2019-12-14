/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.model.Follower;
import projekti.model.FollowerRepository;

/**
 *
 * @author ptuomola
 */

@Service
public class FollowerService {
    
    @Autowired
    private FollowerRepository fr;
    
    public void toggleFollowing(Account followingAccount, Account followedAccount)
    {
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
    }
    
}

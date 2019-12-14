/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.model.Block;
import projekti.model.BlockRepository;
import projekti.model.FollowerRepository;

/**
 *
 * @author ptuomola
 */

@Service
public class BlockService {
    
    @Autowired
    private BlockRepository br;
    
    @Autowired
    private FollowerRepository fr;
    
    @Transactional
    public void toggleBlock(Account blockedUser, Account blockingUser) 
    {
        Block block = br.findByBlockedAccountAndBlockingAccount(blockedUser, blockingUser);
        
        if(block != null)
        {
            br.delete(block);
        }
        else
        {
            // We need to remove all follows before blocking
            fr.deleteByFollowingAccountAndFollowedAccount(blockedUser, blockingUser);
            fr.deleteByFollowingAccountAndFollowedAccount(blockingUser, blockedUser);
            
            block = new Block();
            block.setBlockedAccount(blockedUser);
            block.setBlockingAccount(blockingUser);
            block.setCreatedOn(new Date());
            br.save(block);
        }
    }
}

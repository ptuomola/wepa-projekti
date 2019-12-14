/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ptuomola
 */
public interface FollowerRepository extends JpaRepository<Follower, Long>
{
    @EntityGraph(attributePaths = {"followingAccount"})
    public List<Follower> findByFollowedAccount(Account account);
    
    public Follower getByFollowingAccountAndFollowedAccount(Account following, Account followed);
    
    @EntityGraph(attributePaths = {"followedAccount"})
    public List<Follower>  findByFollowingAccount(Account account);
    
        
    public Long countByFollowingAccount(Account account);
    public Long countByFollowedAccount(Account account);

    public void deleteByFollowingAccountAndFollowedAccount(Account followingUser, Account followedUser);
}

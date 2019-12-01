/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ptuomola
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByUsernameIgnoreCase(String username);
    public Account findByUrlStringIgnoreCase(String urlString);
    
    @Query("select acc from Account acc where upper(acc.username) like upper(concat('%', ?1,'%')) or upper(acc.name) like upper(concat('%', ?1,'%'))")
    public List<Account> searchAccounts(String searchString);
}

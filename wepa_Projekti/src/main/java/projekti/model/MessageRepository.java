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
public interface MessageRepository extends JpaRepository<Message, Long> {
   
    @Query(value = "SELECT * FROM MESSAGE WHERE SENDER_ID = ?1 OR SENDER_ID IN (SELECT FOLLOWED_ID FROM FOLLOWER WHERE FOLLOWING_ID = ?1)  ORDER BY SENT_TIME DESC LIMIT 25",
            nativeQuery = true)
    List<Message> getMessagesForDisplay(Account account);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ptuomola
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByLikedMessageAndLikingAccount(Message message, Account account);
    Like findByLikedImageAndLikingAccount(Image image, Account account);
}

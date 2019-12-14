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
import projekti.model.Image;
import projekti.model.ImageRepository;
import projekti.model.Like;
import projekti.model.LikeRepository;
import projekti.model.Message;
import projekti.model.MessageRepository;

/**
 *
 * @author ptuomola
 */

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository lr;
    
    @Autowired
    private ImageRepository ir;
    
    @Autowired
    private MessageRepository mr;

    public void toggleLike(Image image, Account user) {
        
        Like like = lr.findByLikedImageAndLikingAccount(image, user);

        if(like != null)
        {
            lr.delete(like);
            image.setNumLikes(image.getNumLikes() - 1);
            ir.save(image);
        }
        else
        {
            like = new Like();
            like.setLikedImage(image);
            like.setLikingAccount(user);
            lr.save(like);
            image.setNumLikes(image.getNumLikes() + 1);
            ir.save(image);
        }
    }

    public void toggleLike(Message message, Account user) 
    {
        Like like = lr.findByLikedMessageAndLikingAccount(message, user);

        if(like != null)
        {
            lr.delete(like);
            message.setNumLikes(message.getNumLikes() - 1);
            mr.save(message);
        }
        else
        {
            like = new Like();
            like.setLikedMessage(message);
            like.setLikingAccount(user);
            lr.save(like);
            message.setNumLikes(message.getNumLikes() + 1);
            mr.save(message);
        }
    }
}

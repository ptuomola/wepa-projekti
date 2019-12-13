/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
import projekti.model.Comment;
import projekti.model.CommentRepository;
import projekti.model.Follower;
import projekti.model.FollowerRepository;
import projekti.model.Image;
import projekti.model.Like;
import projekti.model.LikeRepository;
import projekti.model.Message;
import projekti.model.MessageRepository;

/**
 *
 * @author ptuomola
 */

@Controller
public class MessageController {
    
    @Autowired
    private MessageRepository mr;
    
    @Autowired
    private AccountRepository ar;

    @Autowired
    private LikeRepository lr;
    
    @Autowired
    private CommentRepository cr;
            
    @PostMapping("/messages")
    public String postMessage(@RequestParam String messageText, HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        String username = auth.getName();        
        Account user = ar.findByUsernameIgnoreCase(username);
        
        if(user == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        Message message = new Message();
        
        message.setSender(user);
        message.setSentTime(new Date());
        message.setMessageText(messageText);
        
        mr.save(message);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @Transactional
    @PostMapping("/messages/{id}/toggleLiking")
    public String toggleLike(@PathVariable Long id, HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        String username = auth.getName();        
        Account user = ar.findByUsernameIgnoreCase(username);
       
        Message message = mr.getOne(id);
        
        if(user == null || message == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
       
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

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @PostMapping("/messages/{id}/comment")
    public String postMessageComment(@PathVariable Long id, @RequestParam String commentText, HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        String username = auth.getName();        
        Account user = ar.findByUsernameIgnoreCase(username);
       
        Message message = mr.getOne(id);
        
        if(user == null || message == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        } 
        
        Comment comment = new Comment();
        comment.setCommentedMessage(message);
        comment.setCommentText(commentText);
        comment.setCommentTime(new Date());
        comment.setCommentingAccount(user);
        cr.save(comment);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}

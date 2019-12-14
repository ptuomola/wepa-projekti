/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import projekti.model.Account;
import projekti.model.AccountRepository;
import projekti.model.Comment;
import projekti.model.CommentRepository;
import projekti.model.Image;
import projekti.model.ImageRepository;
import projekti.model.Like;
import projekti.model.LikeRepository;
import projekti.model.Message;
import projekti.services.AccountService;
import projekti.services.LikeService;

/**
 *
 * @author ptuomola
 */

@Controller
public class ImageController {
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private LikeRepository lr;    
    
    @Autowired
    private CommentRepository cr; 
    
    @Autowired
    private AccountService as;

    @Autowired
    private LikeService ls;
    
    
    @PostMapping("/images")
    public String saveImage(Model model, @RequestParam String description, @RequestParam("file") MultipartFile file) throws IOException {
        String uploadError = null;
        
        Account owner = as.getLoggedInAccount();
        
        if(description == null || description.equals(""))
        {
            uploadError = "Please provide a description";
        }
        
        if(file == null || file.isEmpty() || file.getSize() == 0)
        {
            uploadError = "Please provide a file to upload";
        }
        
        if(uploadError == null && imageRepository.findByOwnerOrderByIdAsc(owner).size() >= 10)
        {
            uploadError = "You already have the maximum number of images in your gallery";
        }
        
        MediaType mt = MediaType.parseMediaType(file.getContentType());
        
        if(uploadError == null && !mt.getType().equals("image"))
        {
            uploadError = "The selected file is not an image";
        }
        
        if(uploadError != null)
        {
            getImages(model);
            model.addAttribute("uploadError", uploadError);
            return "images";
        }
        
        Image image = new Image();

        image.setDescription(description);
        image.setMediaType(file.getContentType());
        image.setSize(file.getSize());
        image.setContent(file.getBytes());
        
        image.setOwner(owner);

        imageRepository.save(image);

        return "redirect:/images";
    }
    
    @PostMapping("/images/{id}/toggleProfileImage")
    public String setProfileImage(@PathVariable Long id)
    {
        Account owner = as.getLoggedInAccount();
        Image image = imageRepository.getOne(id);
        
        if(!image.getOwner().equals(owner))
            return "redirect:/images";
            
        if(owner.getProfileImage() == image)
            owner.setProfileImage(null);
        else 
            owner.setProfileImage(image);

        accountRepository.save(owner);
        
        return "redirect:/images";        
    }
    
    @Transactional
    @PostMapping("/images/{id}/delete")
    public String deleteImage(@PathVariable Long id)
    {
        Account owner = as.getLoggedInAccount();
        Image image = imageRepository.getOne(id);
        
        if(!image.getOwner().equals(owner))
            return "redirect:/images";

        if(image.equals(owner.getProfileImage()))
        {
            owner.setProfileImage(null);
            accountRepository.save(owner);
        }

        if(image.getNumLikes() > 0)
        {
            lr.deleteByLikedImage(image);
        }
        
        imageRepository.deleteById(id);
        return "redirect:/images";
    }
        
    @GetMapping("/images")
    public String getImages(Model model)
    {
        Account owner = as.getLoggedInAccount();
        List<Image> images = imageRepository.findByOwnerOrderByIdAsc(owner);
        model.addAttribute("count", images.size());
        model.addAttribute("images", images);
        model.addAttribute("profileImage", owner.getProfileImage());
        
        return "images";
    }
    
    @GetMapping("/images/{id}/content")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id)
    {
        Account owner = as.getLoggedInAccount();
        Image image = imageRepository.getOne(id);
        
        if(!image.getOwner().equals(owner) && image.getOwner().getProfileImage() != image)
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            
        final HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(image.getSize());

        return new ResponseEntity<>(image.getContent(), headers, HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/images/{id}/toggleLiking")
    public String toggleLike(@PathVariable Long id, HttpServletRequest request)
    {
        Account user = as.getLoggedInAccount();
        Image image = imageRepository.getOne(id);
        
        if(user == null || image == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        
        ls.toggleLike(image, user);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/images/{id}/comment")
    public String postImageComment(@PathVariable Long id, @RequestParam String commentText, HttpServletRequest request)
    {
        Account user = as.getLoggedInAccount();
        Image image = imageRepository.getOne(id);
        
        if(user == null || image == null)
        {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        } 
        
        Comment comment = new Comment();
        comment.setCommentedImage(image);
        comment.setCommentText(commentText);
        comment.setCommentTime(new Date());
        comment.setCommentingAccount(user);
        cr.save(comment);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.fluentlenium.core.annotation.Page;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.openqa.selenium.By;

/**
 *
 * @author ptuomola
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageTest extends BaseTest {

    @Page
    RegistrationPage rp; 
    
    @Page
    LoginPage lp; 
    
    @Page
    ImagePage ip; 
    
    public ImageTest() {
    }
    
    @Test
    public void uploadImage()
    {
        rp.go();
        rp.registerUser("imageuser1", "Imageuser1");
        lp.isAt();
        lp.loginUser("imageuser1", "Imageuser1");
        assertTrue(pageSource().contains("Your wall"));
        ip.go();
        assertTrue(pageSource().contains("0 images"));
        ip.uploadFile("./test-image.jpeg", "Test image");
        ip.isAt();
        assertTrue(pageSource().contains("1 images"));
    }
    
    @Test
    public void deleteImage()
    {
        rp.go();
        rp.registerUser("imageuser2", "Imageuser2");
        lp.isAt();
        lp.loginUser("imageuser2", "Imageuser2");
        assertTrue(pageSource().contains("Your wall"));
        ip.go();
        assertTrue(pageSource().contains("0 images"));
        ip.uploadFile("./test-image.jpeg", "Test image");
        ip.isAt();
        assertTrue(pageSource().contains("1 images"));
        find("#delete").first().click();
        assertTrue(pageSource().contains("0 images"));
    }
    
    @Test
    public void uploadImageNoDescription()
    {
        String username = "imageuser3";
        String password = "Imageuser3";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        assertTrue(pageSource().contains("Your wall"));
        ip.go();
        assertTrue(pageSource().contains("0 images"));
        ip.uploadFile("./test-image.jpeg", "");
        ip.isAt();
        assertTrue(pageSource().contains("0 images"));
    }
    
    @Test
    public void uploadMax10Images()
    {
        String username = "imageuser4";
        String password = "Imageuser4";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        assertTrue(pageSource().contains("Your wall"));
        ip.go();
        assertTrue(pageSource().contains("0 images"));
        
        for(int i = 1; i <= 10; i++)
        {
            ip.uploadFile("./test-image.jpeg", "Image " + i);
            ip.isAt();
            assertTrue(pageSource().contains("" + i + " images"));  
            assertTrue(pageSource().contains("Image " + i));      
        }
        
        ip.uploadFile("./test-image.jpeg", "Image 11");
        ip.isAt();
        assertTrue(pageSource().contains("10 images"));     
    }
    
    @Test
    public void setProfileImage()
    {
        String username = "imageuser5";
        String password = "Imageuser5";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        assertTrue(pageSource().contains("Your wall"));
        ip.go();
        assertTrue(pageSource().contains("0 images"));
        
        for(int i = 1; i <= 3; i++)
        {
            ip.uploadFile("./test-image.jpeg", "Image " + i);
            ip.isAt();
            assertTrue(pageSource().contains("" + i + " images"));  
            assertTrue(pageSource().contains("Image " + i));      
        }
        
        find(By.xpath("//button[value()='Set as profile image']")).first().click();
        assertTrue(pageSource().contains("Unset as profile image"));
    
        String image = $("img").first().attribute("src");
        System.out.println("image: " + image);
        goTo("/");
        assertTrue(pageSource().contains(image));
    }

    
}



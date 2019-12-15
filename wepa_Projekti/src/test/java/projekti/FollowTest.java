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
public class FollowTest extends BaseTest {

    @Page
    RegistrationPage rp; 
    
    @Page
    LoginPage lp; 
    
    @Page
    ImagePage ip; 
    
    @Page
    SearchPage sp;
    
    @Page
    WallPage wp;
    
    public FollowTest() {
    }
    
    @Test
    public void userSeesFollowers()
    {
        String username = "followuser1";
        String password = "Followuser1";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        
        username = "followuser2";
        password = "Followuser2";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        
        sp.go();
        sp.searchUser("followuser1");
        
        find(By.linkText("followuser1")).first().click();
        assertTrue(pageSource().contains("@followuser1"));
        find(By.xpath("//input[@value='Start following']")).first().click();
        
        goTo("/followers");
        assertTrue(pageSource().contains("followuser1"));
        
        goTo("/logout");
        lp.go();
        lp.loginUser("followuser1", "Followuser1"); 
        goTo("/followers");
        assertTrue(pageSource().contains("followuser2"));
    }
    
    @Test
    public void userSeesFollowedPosts()
    {
        String username = "followuser3";
        String password = "Followuser3";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.go();
        wp.postWall("This is a post by followuser3");
        assertTrue(pageSource().contains("This is a post by followuser3"));
        
        username = "followuser4";
        password = "Followuser4";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        
        wp.isAt();
        assertFalse(pageSource().contains("This is a post by followuser3"));
        
        sp.go();
        sp.searchUser("followuser3");
        
        find(By.linkText("followuser3")).first().click();
        assertTrue(pageSource().contains("@followuser3"));
        find(By.xpath("//input[@value='Start following']")).first().click();
        
        wp.go();
        assertTrue(pageSource().contains("This is a post by followuser3"));
        
        sp.go();
        sp.searchUser("followuser3");
        
        find(By.linkText("followuser3")).first().click();
        assertTrue(pageSource().contains("@followuser3"));
        find(By.xpath("//input[@value='Stop following']")).first().click();

        wp.go();
        assertFalse(pageSource().contains("This is a post by followuser3"));        
    }
    
    @Test
    public void userCanBlockFollowers()
    {
        String username = "followuser5";
        String password = "Followuser5";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.go();
        wp.postWall("This is a post by followuser5");
        assertTrue(pageSource().contains("This is a post by followuser5"));
        
        username = "followuser6";
        password = "Followuser6";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        
        wp.isAt();
        assertFalse(pageSource().contains("This is a post by followuser5"));
        
        sp.go();
        sp.searchUser("followuser5");
        
        find(By.linkText("followuser5")).first().click();
        assertTrue(pageSource().contains("@followuser5"));
        find(By.xpath("//input[@value='Start following']")).first().click();
        
        wp.go();
        assertTrue(pageSource().contains("This is a post by followuser5"));
        
        goTo("/logout");
        lp.isAt();
        lp.loginUser("followuser5", "Followuser5");    
        
        sp.go();
        sp.searchUser("followuser6");
        
        find(By.linkText("followuser6")).first().click();
        assertTrue(pageSource().contains("@followuser6"));
        find(By.xpath("//input[@value='Block']")).first().click();

        goTo("/logout");
        lp.isAt();
        lp.loginUser("followuser6", "Followuser6");    
        
        wp.go();
        assertFalse(pageSource().contains("This is a post by followuser5")); 
    }
    
    
}



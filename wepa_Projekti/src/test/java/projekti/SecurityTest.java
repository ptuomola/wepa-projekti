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
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ptuomola
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest extends BaseTest {

    @Page
    RegistrationPage rp; 
    
    @Page
    SearchPage sp;
    
    @Page
    LoginPage lp;
    
    @Page
    WallPage wp;


    @Test
    public void notLoggedInUserCanUseSearch()
    {
        rp.go();
        rp.registerUser("securityuser1", "Securityuser1");
        sp.go();
        sp.searchUser("securityuser1");
        assertTrue(pageSource().contains("securityuser1"));
    }
    
    @Test
    public void notLoggedInUserCanViewWalls()
    {
        String username = "securityuser3";
        String password = "Securityuser3";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        
        wp.isAt();
        wp.postWall("Wall message 1");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 1"));
        assertTrue(pageSource().contains("0 likes"));
        
        goTo("/logout");
        
        sp.go();
        sp.searchUser("securityuser3");
                
        find(By.linkText("securityuser3")).first().click();
        assertTrue(pageSource().contains("Wall message 1"));
        assertFalse(pageSource().contains("Start following"));
        assertFalse(pageSource().contains("Block"));
        
        assertTrue(find("#commentText").isEmpty());
        assertTrue(find("#postComment").isEmpty());
        assertTrue(find("#likebutton").isEmpty());
        assertTrue(find("#accountgallery").isEmpty());
    }
    
}



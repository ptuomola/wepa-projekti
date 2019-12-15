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

/**
 *
 * @author ptuomola
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest extends BaseTest {

    @Page
    RegistrationPage rp; 
    
    @Page
    LoginPage lp; 
    
    public LoginTest() {
    }
    
    @Test
    public void nonExistentUser()
    {
        lp.go();
        lp.loginUser("blah", "blah");
        assertTrue(pageSource().contains("Invalid username and password"));
    }
    
    @Test
    public void existingUser()
    {
        rp.go();
        rp.registerUser("loginuser1", "Loginuser1");
        lp.isAt();
        lp.loginUser("loginuser1", "Loginuser1");
        assertTrue(pageSource().contains("Your wall"));
    }
    
    @Test
    public void logoutUser()
    {
        rp.go();
        rp.registerUser("loginuser2", "Loginuser2");
        lp.isAt();
        lp.loginUser("loginuser2", "Loginuser2");
        assertTrue(pageSource().contains("Your wall"));
        goTo("/logout");
        lp.isAt();
        assertTrue(pageSource().contains("You have been logged out successfully."));
    }
}



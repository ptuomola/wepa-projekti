/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

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
public class RegistrationTest extends BaseTest {
    
    @LocalServerPort
    private Integer port;
    
    public RegistrationTest() {
    }
    
    @Test
    public void registerUser()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser1");
        find("#password").fill().with("Testuser1");
        find("#passwordConfirm").fill().with("Testuser1");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));
    }
    
    @Test
    public void userNameTooShort()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("te");
        find("#password").fill().with("Testuser1");
        find("#passwordConfirm").fill().with("Testuser1");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("size must be between 3 and 30"));
    }
    
    @Test
    public void passwordTooShort()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser2");
        find("#password").fill().with("1234567");
        find("#passwordConfirm").fill().with("1234567");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("Password must be at least 8 characters long"));
    }
    
    @Test
    public void passwordDoesntMatch()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser2");
        find("#password").fill().with("Testuser2");
        find("#passwordConfirm").fill().with("Testuser1");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("do not match"));
    }
    
    @Test
    public void passwordNoUppercase()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser2");
        find("#password").fill().with("testuser1");
        find("#passwordConfirm").fill().with("testuser1");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("uppercase"));
    }    
    
    @Test
    public void passwordNoLowercase()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser2");
        find("#password").fill().with("TESTUSER1");
        find("#passwordConfirm").fill().with("TESTUSER1");
        find("#name").fill().with("Test User 1");
        find("#urlString").fill().with("testuser1");
        find("form").first().submit();
        assertTrue(pageSource().contains("lowercase"));
    }      

    @Test
    public void usernameMatches()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser3");
        find("#password").fill().with("Testuser3");
        find("#passwordConfirm").fill().with("Testuser3");
        find("#name").fill().with("Test User 3");
        find("#urlString").fill().with("testuser3");
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));
        
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser3");
        find("#password").fill().with("Testuser3");
        find("#passwordConfirm").fill().with("Testuser3");
        find("#name").fill().with("Test User 3");
        find("#urlString").fill().with("testuser4");
        find("form").first().submit();
        assertTrue(pageSource().contains("selected username is already in use"));
    }   
    
    @Test
    public void urlStringMatches()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser4");
        find("#password").fill().with("Testuser4");
        find("#passwordConfirm").fill().with("Testuser4");
        find("#name").fill().with("Test User 4");
        find("#urlString").fill().with("testuser4");
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));
        
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser5");
        find("#password").fill().with("Testuser5");
        find("#passwordConfirm").fill().with("Testuser5");
        find("#name").fill().with("Test User 5");
        find("#urlString").fill().with("testuser4");
        find("form").first().submit();
        assertTrue(pageSource().contains("selected URL suffix is already in use"));
    }

    @Test
    public void urlStringNoWhitespace()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser5");
        find("#password").fill().with("Testuser5");
        find("#passwordConfirm").fill().with("Testuser5");
        find("#name").fill().with("Test User 5");
        find("#urlString").fill().with("test user5");
        find("form").first().submit();
        assertTrue(pageSource().contains("URL string contains illegal characters"));
    }      
    
    @Test
    public void urlStringNoSpecialCharacters()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser5");
        find("#password").fill().with("Testuser5");
        find("#passwordConfirm").fill().with("Testuser5");
        find("#name").fill().with("Test User 5");
        find("#urlString").fill().with("test!user5");
        find("form").first().submit();
        assertTrue(pageSource().contains("URL string contains illegal characters"));
    }  
    
    @Test 
    public void checkUrlSuffixHomepage()
    {
        goTo("http://localhost:" + port + "/register");
        find("#username").fill().with("testuser6");
        find("#password").fill().with("Testuser6");
        find("#passwordConfirm").fill().with("Testuser6");
        find("#name").fill().with("Test User 6");
        find("#urlString").fill().with("testuser6");
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));   
        
        goTo("/accounts/testuser6");
        assertTrue(pageSource().contains("@testuser6"));   
        goTo("/accounts/tesTUser6");
        assertTrue(pageSource().contains("@testuser6"));          
    }  
    
}



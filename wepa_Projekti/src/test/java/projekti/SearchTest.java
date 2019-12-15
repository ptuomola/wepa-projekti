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
public class SearchTest extends BaseTest {

    @Page
    RegistrationPage rp; 
    
    @Page
    SearchPage sp;
    
    @Test
    public void findOne()
    {
        rp.go();
        rp.registerUser("searchoneuser", "Bobby Search One", "Searchuser1");
        sp.go();
        sp.searchUser("Bobby Search");
        assertTrue(pageSource().contains("Bobby Search One"));
        sp.go();
        sp.searchUser("searchoneuser");
        assertTrue(pageSource().contains("Bobby Search One"));
    }
    
    @Test
    public void findThree()
    {
        rp.go();
        rp.registerUser("searchuser2", "Alice Search Three", "Searchuser2");  
        rp.go();
        rp.registerUser("searchuser3", "David Search Three", "Searchuser3");
        rp.go();
        rp.registerUser("searchuser4", "Bobby Search Three", "Searchuser4");   

        sp.go();
        sp.searchUser("Search Three");
        assertTrue(pageSource().contains("Bobby Search Three"));
        assertTrue(pageSource().contains("Alice Search Three"));
        assertTrue(pageSource().contains("David Search Three"));
        
        sp.go();
        sp.searchUser("searchuser");
        assertTrue(pageSource().contains("Bobby Search Three"));
        assertTrue(pageSource().contains("Alice Search Three"));
        assertTrue(pageSource().contains("David Search Three"));
    }
}



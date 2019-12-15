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
public class WallTest extends BaseTest {

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
    
    public WallTest() {
    }
    
    @Test
    public void singlePost()
    {
        String username = "walluser1";
        String password = "Walluser1";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.isAt();
        wp.postWall("Wall message 1");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 1"));
    }
    
    @Test
    public void max25Posts()
    {
        String username = "walluser2";
        String password = "Walluser2";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.isAt();
        
        for(int i = 1; i <= 25; i++)
        {
            wp.postWall("Wall message " + i + ".");
            wp.isAt();
            assertTrue(pageSource().contains("Wall message " + i + "."));
        }
        
        wp.postWall("Wall message 26.");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 26."));
        assertFalse(pageSource().contains("Wall message 1."));
    }
    
    @Test
    public void likePost()
    {
        String username = "walluser3";
        String password = "Walluser3";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.isAt();
        
        wp.isAt();
        wp.postWall("Wall message 1");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 1"));
        assertTrue(pageSource().contains("0 likes"));
        
        find("#likebutton").first().click();
        assertTrue(pageSource().contains("1 likes"));
        
        find("#likebutton").first().click();
        assertTrue(pageSource().contains("0 likes"));
        
    }
    
    @Test
    public void commentPost()
    {
        String username = "walluser4";
        String password = "Walluser4";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.isAt();
        
        wp.isAt();
        wp.postWall("Wall message 1");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 1"));
        assertTrue(pageSource().contains("0 likes"));
    
        assertTrue(pageSource().contains("0 comments"));
     
        find("#commentText").fill().with("This is a test comment");
        find("#postComment").first().click();
        
        assertTrue(pageSource().contains("1 comments"));
        assertTrue(pageSource().contains("This is a test comment"));
    }

    @Test
    public void max10Comments()
    {
        String username = "walluser5";
        String password = "Walluser5";
        
        rp.go();
        rp.registerUser(username, password);
        lp.isAt();
        lp.loginUser(username, password);
        wp.isAt();
        
        wp.isAt();
        wp.postWall("Wall message 1");
        wp.isAt();
        assertTrue(pageSource().contains("Wall message 1"));
        assertTrue(pageSource().contains("0 likes"));
    
        assertTrue(pageSource().contains("0 comments"));
     
        for(int i = 1; i <= 10; i++)
        {
            find("#commentText").fill().with("This is a test comment #" + i + ".");
            find("#postComment").first().click();

            assertTrue(pageSource().contains("" + i + " comments"));
            assertTrue(pageSource().contains("This is a test comment #" + i + "."));
        }
        
        find("#commentText").fill().with("This is a test comment #11.");
        find("#postComment").first().click();

        assertTrue(pageSource().contains("11 comments"));
        assertTrue(pageSource().contains("Showing 10 most recent comments"));
        assertFalse(pageSource().contains("This is a test comment #1."));
        
    }

    
}



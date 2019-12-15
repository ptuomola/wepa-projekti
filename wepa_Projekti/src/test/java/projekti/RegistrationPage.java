/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.fluentlenium.core.FluentPage;
import static org.junit.Assert.assertTrue;

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
public class RegistrationPage extends FluentPage {
    
    public String getUrl() {
        return "/register";
    }
    public void isAt() {
        assertTrue(window().title().equals("Registration"));
    }
    
    public void registerUser(String username, String password) {
        find("#username").fill().with(username);
        find("#password").fill().with(password);
        find("#passwordConfirm").fill().with(password);
        find("#name").fill().with(username);
        find("#urlString").fill().with(username);
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));        
    }
    
    public void registerUser(String username, String name, String password) {
        find("#username").fill().with(username);
        find("#password").fill().with(password);
        find("#passwordConfirm").fill().with(password);
        find("#name").fill().with(name);
        find("#urlString").fill().with(username);
        find("form").first().submit();
        assertTrue(pageSource().contains("Registration successful"));        
    }
    
}

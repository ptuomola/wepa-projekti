/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import static org.junit.Assert.assertTrue;


/**
 *
 * @author ptuomola
 */

@PageUrl("/")
public class WallPage extends FluentPage {
    
    public void isAt() {
        assertTrue(pageSource().contains("Your wall"));
    }
    
    public void postWall(String message) {
        find("#message").fill().with(message);
        find("#postbutton").first().click();
    }
}

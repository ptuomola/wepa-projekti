/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ptuomola
 */

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(Model model, String error, String logout, String newUser) {
        System.out.println("in login, logout is " + logout);
        
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        if (newUser != null)
            model.addAttribute("msg", "Registration successful. Please log in.");

        return "login";
    }
    
    @GetMapping("/logout")
    public String logout()
    {
        return "redirect:/login?logout";
    }
}

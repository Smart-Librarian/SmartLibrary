package com.example.SmartLibrarian.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SmartLibrarian.Model.UserMaster;
import com.example.SmartLibrarian.Service.SecurityUserDetailsService;

@Controller
public class MainController {

    @Autowired
    private SecurityUserDetailsService userDetailsManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping({"/login", "logout"})
    public String login(Authentication a, Model model) {
        
    	if(a == null || a instanceof AnonymousAuthenticationToken) {
    		return "login";
    	}
    	
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication a, Model model) {
        model.addAttribute("user", a.getPrincipal());
        UserMaster user = (UserMaster) a.getPrincipal();
        if("U".equalsIgnoreCase(user.getRole())) {
        	return "user";
        }
        return "admin";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/register")
    public void addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserMaster user = new UserMaster(); user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountNonLocked(true); userDetailsManager.createUser(user);
    }
    
}

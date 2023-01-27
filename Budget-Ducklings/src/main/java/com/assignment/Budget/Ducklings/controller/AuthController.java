package com.assignment.Budget.Ducklings.controller;

import com.assignment.Budget.Ducklings.exception.InvalidPasswordException;
import com.assignment.Budget.Ducklings.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/auth/*")
public class AuthController {
    private UserService userService;

    public AuthController() {
        userService = new UserService();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public String handleInvalidPassword(InvalidPasswordException ex, HttpSession session, HttpServletRequest req) {

        Object loginAttempts = session.getAttribute("loginAttempts");
        if(loginAttempts == null) {
            loginAttempts = 0;
        }

        session.setAttribute("loginAttempts", (int) loginAttempts + 1);

        return "redirect:login?error=" + ex.getMessage();
    }


    @GetMapping("login")
    public String getLoginPage() {
        return "loginPage";
    }

    @PostMapping("login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {

        var user = userService.getUser(username, password);

        if (user != null){
            session.setMaxInactiveInterval(60 * 30);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId());
            return "redirect:/invoice";
        } else {
            throw new InvalidPasswordException("Invalid password attempt", username);
        }

    }

    @GetMapping("logout")
    public String logout(HttpSession session) throws IOException {
        session.invalidate(); //Invalidate - empty the session
        return "redirect:/auth/login";
    }


}


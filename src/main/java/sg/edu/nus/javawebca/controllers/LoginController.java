package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.UserInterface;


@Controller
public class LoginController {
  @Autowired
  private UserInterface userInterface;
  
  @GetMapping(value = {"/", "/login", "/home"})
  public String login(Model model) {
    model.addAttribute("user", new User());
    
    return "login";
  }
  
  @RequestMapping(value = "/home/authenticate")
  public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
      HttpSession session) {
    if (bindingResult.hasErrors()) {
      return "login";
    } 
    
    User u = userInterface.authenticate(user.getAccount(), user.getPassword());
    
    if (u == null) {
      model.addAttribute("loginMessage", "Incorrect username/password");
      return "login";
    }
    else if (u.getRole()==0) {
      return "redirect:/Admin/users";
    }
    
    return "redirect:/staff/leaveApplication/history";
  }
  
  @RequestMapping(value = "/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }
}
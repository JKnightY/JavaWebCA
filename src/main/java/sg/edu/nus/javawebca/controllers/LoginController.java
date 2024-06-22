package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.UserInterface;

@Controller
public class LoginController {
  @Autowired
  private UserInterface userInterface;

  @GetMapping(value = {"/", "/login", "/home"})
  public String login(Model model) {
    model.addAttribute("user", new User());
    return "/login";
  }

  @RequestMapping(value = "/login")
  public String login(@ModelAttribute User user, HttpSession session, Model model) {
    if (validateUser(user.getAccount(), user.getPassword())) {
      User inuser = userInterface.findByAccount(user.getAccount());
      session.setAttribute("user", inuser);
      if (inuser.getRole() == 0) {
        return "redirect:/Admin/users";
      } else if (inuser.getRole() == 1) {
        return "redirect:/staff/leaveApplication/history";
      } else if (inuser.getRole() == 2) {
        return "redirect:http://localhost:3000/";
      } else {
        model.addAttribute("loginMessage", "Unauthorized access");
        return "login";
      }
    } else {
      model.addAttribute("loginMessage", "Invalid username or password.");
      return "login";
    }
  }

  private boolean validateUser(String account, String password) {
    User user = userInterface.findByAccount(account);
    return user != null && user.getPassword().equals(password);
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }
}
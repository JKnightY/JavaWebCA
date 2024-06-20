package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  private UserInterface userInterface;

  @GetMapping(value = {"/", "/login", "/home"})
  public String login(Model model) {
    model.addAttribute("user", new User());
    return "login";
  }

  @PostMapping("/home/authenticate")
  public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, HttpSession session) {
    if (bindingResult.hasErrors()) {
      return "login";
    }

    logger.debug("Account: {}", user.getAccount());
    logger.debug("Password: {}", user.getPassword());

    User authenticatedUser = userInterface.authenticate(user.getAccount(), user.getPassword());

    if (authenticatedUser == null) {
      model.addAttribute("loginMessage", "Incorrect username/password");
      return "login";
    }

    session.setAttribute("loggedInUser", authenticatedUser); // 保存用户信息到会话中

    if (authenticatedUser.getRole() == 0) {
      return "redirect:/Admin/users";
    }

    return "redirect:/staff/leaveApplication/history";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }
}

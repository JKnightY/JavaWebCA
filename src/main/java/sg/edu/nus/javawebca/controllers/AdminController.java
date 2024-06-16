package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.AdminService;
import java.util.Optional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.findAllUsers());
        return "user-list";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        Optional<User> user = adminService.findUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user-details";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping("/users/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "user-create";
    }

    @RequestMapping("/users/save")
    public String saveUser(@ModelAttribute("user") @Valid User inuser , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-create";
        }
        if (inuser.getId() == null) {
            adminService.createUser(inuser);
        } else {
            adminService.updateUser(inuser);
        }
        return "redirect:/Admin/users";
    }

    @RequestMapping("/users/delete")
    public String deleteUser(@RequestParam int id) {
        adminService.deleteUser(id);
        return "redirect:/users";
    }

    @RequestMapping("/users/update")
    public String updateUser(@RequestParam int id, Model model) {
        Optional<User> user = adminService.findUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "user-update";
    }
}

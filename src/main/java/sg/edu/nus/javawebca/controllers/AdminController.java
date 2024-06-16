package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.AdminService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<User> listUsers() {
        return adminService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> optuser = adminService.findUserById(id);
        if (optuser.isPresent()) {
            User user = optuser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

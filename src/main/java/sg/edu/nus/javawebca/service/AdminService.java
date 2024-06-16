package sg.edu.nus.javawebca.service;

import sg.edu.nus.javawebca.models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> findAllUsers();
    User createUser(User user);
    void deleteUser(int id);
    Optional<User> findUserById(int id);
    User updateUser(User user);
}

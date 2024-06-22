package sg.edu.nus.javawebca.services;

import sg.edu.nus.javawebca.models.LeaveType;
import sg.edu.nus.javawebca.models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> findAllUsers();
    void deleteUser(User user);
    Optional<User> findUserById(int id);
    User updateUser(User user);
}

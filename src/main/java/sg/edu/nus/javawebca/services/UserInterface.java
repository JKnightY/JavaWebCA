package sg.edu.nus.javawebca.services;

import java.util.List;

import sg.edu.nus.javawebca.models.User;

public interface UserInterface {
	  List<User> findAllUsers();
	  User findUser(Integer userId);
	  User createUser(User user);
	  User changeUser(User user);
	  void removeUser(User user);
	  User authenticate(String username, String pwd);
}

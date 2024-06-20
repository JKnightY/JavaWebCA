package sg.edu.nus.javawebca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.repositories.UserRepository;


@Service
public class UserInterfaceImpl implements UserInterface {
  @Autowired
  private UserRepository userRepository;
  
  @Transactional
  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  @Override
  public User findUser(Integer userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @Transactional
  @Override
  public User createUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public User changeUser(User user) {
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public void removeUser(User user) {
    userRepository.delete(user);
  }
  @Transactional
  @Override
  public User authenticate(String account, String pwd) {
    return userRepository.findUserByNamePwd(account, pwd);
  }
}
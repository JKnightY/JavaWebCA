package sg.edu.nus.javawebca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.javawebca.models.LeaveType;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.repositories.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<User> findAllUsers() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return adminRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return adminRepository.findById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user){
        return adminRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user){
        adminRepository.delete(user);
    }

}

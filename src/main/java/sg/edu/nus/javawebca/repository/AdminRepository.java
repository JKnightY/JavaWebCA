package sg.edu.nus.javawebca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.javawebca.models.Role;
import sg.edu.nus.javawebca.models.User;

public interface AdminRepository extends JpaRepository<User, Integer> {
}


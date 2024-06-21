package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.javawebca.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByAccount(String account);
	User findByAccountAndPassword(String account, String password);
}

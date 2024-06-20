package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.javawebca.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.account=:account AND u.password=:pwd")
	User findUserByNamePwd(@Param("account") String account, @Param("pwd") String pwd);
}

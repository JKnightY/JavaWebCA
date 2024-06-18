package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.javawebca.models.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
}

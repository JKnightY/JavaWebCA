package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
    @Query("SELECT l FROM LeaveApplication l WHERE l.status = :keyword")
    List<LeaveApplication> findLeaveApplicationsByStatus(@Param("keyword") int keyword);

    @Query("SELECT l FROM LeaveApplication l WHERE l.id = :keyword")
    LeaveApplication findLeaveApplicationsById(@Param("keyword") int keyword);

    List<LeaveApplication> findLeaveApplicationsByUserId(int userId);
}

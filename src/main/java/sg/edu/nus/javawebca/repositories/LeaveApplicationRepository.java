package sg.edu.nus.javawebca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.models.LeaveType;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
    @Query("SELECT l FROM LeaveApplication l WHERE l.status = :keyword")
    List<LeaveApplication> findLeaveApplicationsByStatus(@Param("keyword") LeaveApplicationStatusEnum keyword);

    @Query("SELECT l FROM LeaveApplication l WHERE l.id = :keyword")
    LeaveApplication findLeaveApplicationsById(@Param("keyword") int keyword);

    @Query("SELECT la FROM LeaveApplication la WHERE la.user.id = :userId ORDER BY la.updated_at DESC")
    Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(@Param("userId") int userId, Pageable pageable);

    List<LeaveApplication> findLeaveApplicationsByUserId(int userId);
}

package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;

public interface LeaveApplicationInterface {
    List<LeaveApplication> findAllLeaveApplications();

    Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(int userId, Pageable pageable);

    List<LeaveApplication> findLeaveApplicationsByUserId(int userId);

    LeaveApplication createApplyLeave(LeaveApplication leaveApplication);

    LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

    void deleteLeaveApplication(LeaveApplication leaveApplication);

    LeaveApplication findLeaveApplicationById(int id);
}

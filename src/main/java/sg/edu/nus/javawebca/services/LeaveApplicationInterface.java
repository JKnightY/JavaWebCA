package sg.edu.nus.javawebca.services;

import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;

public interface LeaveApplicationInterface {
    List<LeaveApplication> findAllLeaveApplications();

    LeaveApplication createApplyLeave(LeaveApplication leaveApplication);

    LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

    void deleteLeaveApplication(LeaveApplication leaveApplication);

    LeaveApplication findLeaveApplicationById(int id);
}

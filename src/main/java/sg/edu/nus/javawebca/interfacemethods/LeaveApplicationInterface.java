package sg.edu.nus.javawebca.interfacemethods;

import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;

import java.util.List;
import java.util.Optional;

public interface LeaveApplicationInterface {
    public List<LeaveApplication> getAllLeaveApplications();
    public List<LeaveApplication> getLeaveApplicationsByStatus(LeaveApplicationStatusEnum status);
    public boolean saveLeaveApplication(LeaveApplication leaveApplication);

    public Optional<LeaveApplication> findById(int id);
}

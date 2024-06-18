package sg.edu.nus.javawebca.interfacemethods;

import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;
import java.util.Optional;

public interface LeaveApplicationInterface {
    public List<LeaveApplication> getAllLeaveApplications();
    public List<LeaveApplication> getLeaveApplicationsByStatus(int status);
    public boolean saveLeaveApplication(LeaveApplication leaveApplication);

    public Optional<LeaveApplication> findById(int id);
}

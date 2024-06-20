package sg.edu.nus.javawebca.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    public ManagerService(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public List<LeaveApplication> getLeaveApplicationsByStatus(LeaveApplicationStatusEnum status) {
        return leaveApplicationRepository.findLeaveApplicationsByStatus(status);
    }

    public Optional<LeaveApplication> getLeaveApplicationById(int id) {
        return leaveApplicationRepository.findById(id);
    }

    @Transactional
    public boolean saveLeaveApplication(LeaveApplication leaveApplication) {
        if(leaveApplicationRepository.save(leaveApplication)==null){
            return false;
        }
        return true;
    }

}

package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.interfacemethods.LeaveApplicationInterface;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.util.List;

@Service
@Transactional
public class LeaveApplicationImplementation implements LeaveApplicationInterface {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Override
    @Transactional
    public boolean saveLeaveApplication(LeaveApplication leaveApplication){
        if(leaveApplicationRepository.save(leaveApplication)==null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<LeaveApplication> getAllLeaveApplications(){
        return leaveApplicationRepository.findAll();
    }
    public List<LeaveApplication> getLeaveApplicationsByStatus(int status){
        return leaveApplicationRepository.findLeaveApplicationsByStatus(status);
    }
}

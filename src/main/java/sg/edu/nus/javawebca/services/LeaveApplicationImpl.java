package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LeaveApplicationImpl implements LeaveApplicationInterface {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Override
    @Transactional
    public List<LeaveApplication> findAllLeaveApplications() {
        return leaveApplicationRepository.findAll();
    }

    @Override
    @Transactional
    public LeaveApplication createApplyLeave(LeaveApplication leaveApplication) {
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication){
        leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public void deleteLeaveApplication(LeaveApplication leaveApplication){
        leaveApplicationRepository.delete(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication findLeaveApplicationById(int id) {
        return leaveApplicationRepository.findById(id).orElse(null);
    }

}

package sg.edu.nus.javawebca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.javawebca.models.LeaveEntitlement;
import sg.edu.nus.javawebca.repositories.LeaveEntitlementRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LeaveEntitlementServiceImpl implements LeaveEntitlementService {
    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepository;

    @Override
    public List<LeaveEntitlement> findAllLeaveEntitlements(){
        return leaveEntitlementRepository.findAll();
    }

    @Override
    @Transactional
    public LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement){
        return leaveEntitlementRepository.save(leaveEntitlement);
    }

    @Override
    @Transactional
    public void deleteLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        leaveEntitlementRepository.delete(leaveEntitlement);
    }

    @Override
    public Optional<LeaveEntitlement> findLeaveEntitlementById(int id) {
        return leaveEntitlementRepository.findById(id);
    }

    @Override
    @Transactional
    public LeaveEntitlement updateLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        return leaveEntitlementRepository.save(leaveEntitlement);
    }
}

package sg.edu.nus.javawebca.services;

import sg.edu.nus.javawebca.models.LeaveEntitlement;
import sg.edu.nus.javawebca.models.LeaveType;

import java.util.List;
import java.util.Optional;

public interface LeaveEntitlementService {
    List<LeaveEntitlement> findAllLeaveEntitlements();
    LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement);
    void deleteLeaveEntitlement(LeaveEntitlement leaveEntitlement);
    Optional<LeaveEntitlement> findLeaveEntitlementById(int id);
    LeaveEntitlement updateLeaveEntitlement(LeaveEntitlement leaveEntitlement);
}

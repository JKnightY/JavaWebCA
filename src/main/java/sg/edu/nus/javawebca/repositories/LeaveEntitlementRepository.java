package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.javawebca.models.LeaveEntitlement;

public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Integer> {
}

package sg.edu.nus.javawebca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.time.LocalDateTime;

@SpringBootTest
public class LeaveApplicationTest {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Test
    void contextLoads() {
        LeaveApplication leave1 = new LeaveApplication();
        //leave1.setEmployeeId(1);
        leave1.setLeaveType('A');
        leave1.setStart_date(LocalDateTime.of(2024, 6, 1, 9, 0));
        leave1.setEnd_date(LocalDateTime.of(2024, 6, 10, 17, 0));
        leave1.setReason("Annual vacation");
        leave1.setStatus(1);
        leave1.setCreated_at(LocalDateTime.now());
        leave1.setUpdated_at(LocalDateTime.now());

        leaveApplicationRepository.save(leave1);
    }
}

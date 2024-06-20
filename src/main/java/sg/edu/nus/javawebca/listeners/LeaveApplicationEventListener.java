package sg.edu.nus.javawebca.listeners;

import sg.edu.nus.javawebca.events.LeaveApplicationStatusChangedEvent;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LeaveApplicationEventListener {
    @Autowired
    private EmailService emailService;

    @EventListener
    public void onLeaveApplicationStatusChanged(LeaveApplicationStatusChangedEvent event) {
        LeaveApplication leaveApplication = event.getLeaveApplication();
        String to = leaveApplication.getUser().getEmail(); // 假设 LeaveApplication 关联了 User 并且 User 有 email 属性
        String subject = "Leave Application Status Changed";
        String text = "Your leave application status has been changed to: " + leaveApplication.getStatus();
        emailService.sendEmail(to, subject, text);
    }
}

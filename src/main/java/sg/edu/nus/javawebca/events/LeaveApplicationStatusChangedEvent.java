package sg.edu.nus.javawebca.events;

import sg.edu.nus.javawebca.models.LeaveApplication;
import org.springframework.context.ApplicationEvent;

public class LeaveApplicationStatusChangedEvent extends ApplicationEvent {
    private LeaveApplication leaveApplication;

    public LeaveApplicationStatusChangedEvent(Object source, LeaveApplication leaveApplication) {
        super(source);
        this.leaveApplication = leaveApplication;
    }

    public LeaveApplication getLeaveApplication() {
        return leaveApplication;
    }
}

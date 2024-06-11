package sg.edu.nus.javawebca.models;

import java.time.LocalDateTime;

public class LeaveHistory {
    private int id;
    private int employeeId;
    private int leaveapplicationId;
    private int leaveType;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private int status;
    private LocalDateTime create_date;

    public LeaveHistory() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getLeaveapplicationId() {
        return leaveapplicationId;
    }

    public void setLeaveapplicationId(int leaveapplicationId) {
        this.leaveapplicationId = leaveapplicationId;
    }

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
}

package sg.edu.nus.javawebca.models;

import java.time.LocalDateTime;

public class LeaveApplication {
    private int id;
    private int employeeId;
    private char leaveType;//"A" = "annual_leave" "M" = "medical_leave" "C" = "compensation_leave"
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String reason;
    private int status;//（1:applied、2:approved、3:rejected、4:cancel、5:updated、6:deleted）
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public LeaveApplication() {}

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

    public char getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(char leaveType) {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}

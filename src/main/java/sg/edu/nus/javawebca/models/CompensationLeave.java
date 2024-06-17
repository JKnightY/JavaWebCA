package sg.edu.nus.javawebca.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class CompensationLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int employeeId;
    private LocalDateTime claim_date;
    private double hours_worked;
    private int status;//（1:applied、2:approved、3:rejected)
    @ManyToOne
    private User approved_by;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    public CompensationLeave() {}

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

    public LocalDateTime getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(LocalDateTime claim_date) {
        this.claim_date = claim_date;
    }

    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(double hours_worked) {
        this.hours_worked = hours_worked;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(User approved_by) {
        this.approved_by = approved_by;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }
}

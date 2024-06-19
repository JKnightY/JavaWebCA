package sg.edu.nus.javawebca.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CompensationLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User approved_by;

    @Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'CANCEL', 'UPDATED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private LeaveApplicationStatusEnum status;//（1:applied、2:approved、3:rejected ...)


    private LocalDate claim_date;
    private double hours_worked;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private String reason;
    private String contact_details;
    private String work_dissemination;
    private String claim_period; // "MORNING", "AFTERNOON", "FULL_DAY"

    public CompensationLeave() {
    }

    public LeaveApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusEnum status) {
        this.status = status;
    }

    public String getClaim_period() {
        return claim_period;
    }

    public void setClaim_period(String claim_period) {
        this.claim_period = claim_period;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public String getWork_dissemination() {
        return work_dissemination;
    }

    public void setWork_dissemination(String work_dissemination) {
        this.work_dissemination = work_dissemination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(LocalDate claim_date) {
        this.claim_date = claim_date;
    }

    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(double hours_worked) {
        this.hours_worked = hours_worked;
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

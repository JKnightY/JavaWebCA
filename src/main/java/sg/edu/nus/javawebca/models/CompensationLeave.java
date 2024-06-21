package sg.edu.nus.javawebca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CompensationLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate endDate;
    @NotNull(message = "Start period is required")
    private String startPeriod;  // "MORNING", "AFTERNOON", "FULL_DAY"
    @NotNull(message = "End period is required")
    private String endPeriod;// "MORNING", "AFTERNOON", "FULL_DAY"
    @Min(value = 0, message = "Hours worked must be greater than or equal to 0")
    private double hours_worked;

    @Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'CANCEL', 'UPDATED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private LeaveApplicationStatusEnum status;//（1:applied、2:approved、3:rejected ...)
    @ManyToOne
    private User approved_by;

    @OneToMany(mappedBy = "compensationLeave", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompensationLeaveHistory> histories = new ArrayList<>();
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private String reason;
    private String contact_details;
    private String work_dissemination;

    public CompensationLeave() {
    }

    public LeaveApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusEnum status) {
        this.status = status;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public List<CompensationLeaveHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<CompensationLeaveHistory> histories) {
        this.histories = histories;
    }
}


package sg.edu.nus.javawebca.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CompensationLeaveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private CompensationLeave compensationLeave;

    private LocalDate startDate;
    private LocalDate endDate;
    private String startPeriod;  // "MORNING", "AFTERNOON", "FULL_DAY"
    private String endPeriod;// "MORNING", "AFTERNOON", "FULL_DAY"
    private LeaveApplicationStatusEnum status;
    private LocalDateTime create_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public LeaveApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
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

    public CompensationLeave getCompensationLeave() {
        return compensationLeave;
    }

    public void setCompensationLeave(CompensationLeave compensationLeave) {
        this.compensationLeave = compensationLeave;
    }
}

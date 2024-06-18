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

    @OneToMany(mappedBy = "CompensationLeaveHistory")
    private List<CompensationLeave> compensationLeaves;

    private LocalDate claim_date;
    private LeaveApplicationStatusEnum status;
    private LocalDateTime create_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CompensationLeave> getCompensationLeaves() {
        return compensationLeaves;
    }

    public void setCompensationLeaves(List<CompensationLeave> compensationLeaves) {
        this.compensationLeaves = compensationLeaves;
    }

    public LocalDate getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(LocalDate claim_date) {
        this.claim_date = claim_date;
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
}

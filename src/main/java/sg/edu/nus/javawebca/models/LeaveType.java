package sg.edu.nus.javawebca.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int maxdays;

    @OneToMany(mappedBy = "leaveType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LeaveApplication> leaveApplications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
        this.leaveApplications = leaveApplications;
    }

    public int getMaxdays() {
        return maxdays;
    }

    public void setMaxdays(int maxdays) {
        this.maxdays = maxdays;
    }

    public LeaveType(){

    }

    public LeaveType(int id){
        this.id = id;
    }
}

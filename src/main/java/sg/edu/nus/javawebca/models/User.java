package sg.edu.nus.javawebca.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "account must be provided")
    private String account;
    private String password;
    //@NotNull(message = "username must be provided")
    private String username;
    private Integer department;
    //"0" = administrative "1" = professional
    //@NotNull(message = "role type must be provided")
    private Integer role;//"0" = "admin" "1" = "staff" "2" = "manager"
    private String email;
    private Integer LeaveApproverid;
    private int annual_leave_entitlement;
    private int annual_leave_entitlement_last;
    private int medical_leave_entitlement;
    private int medical_leave_entitlement_last;
    private int compensation_leave_balance;
    private int compensation_leave_balance_last;
    @OneToMany(mappedBy = "user")
    private List<LeaveApplication> leaveApplications;
    @OneToMany(mappedBy = "approved_by")
    private List<CompensationLeave> approvedLeaves;

    public User() {
    }

    public int getAnnual_leave_entitlement_last() {
        return annual_leave_entitlement_last;
    }

    public void setAnnual_leave_entitlement_last(int annual_leave_entitlement_last) {
        this.annual_leave_entitlement_last = annual_leave_entitlement_last;
    }

    public int getMedical_leave_entitlement_last() {
        return medical_leave_entitlement_last;
    }

    public void setMedical_leave_entitlement_last(int medical_leave_entitlement_last) {
        this.medical_leave_entitlement_last = medical_leave_entitlement_last;
    }

    public int getCompensation_leave_balance_last() {
        return compensation_leave_balance_last;
    }

    public void setCompensation_leave_balance_last(int compensation_leave_balance_last) {
        this.compensation_leave_balance_last = compensation_leave_balance_last;
    }

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
        this.leaveApplications = leaveApplications;
    }

    public List<CompensationLeave> getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(List<CompensationLeave> approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAnnual_leave_entitlement() {
        return annual_leave_entitlement;
    }

    public void setAnnual_leave_entitlement(int annual_leave_entitlement) {
        this.annual_leave_entitlement = annual_leave_entitlement;
    }

    public int getMedical_leave_entitlement() {
        return medical_leave_entitlement;
    }

    public void setMedical_leave_entitlement(int medical_leave_entitlement) {
        this.medical_leave_entitlement = medical_leave_entitlement;
    }

    public int getCompensation_leave_balance() {
        return compensation_leave_balance;
    }

    public void setCompensation_leave_balance(int compensation_leave_balance) {
        this.compensation_leave_balance = compensation_leave_balance;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getLeaveApproverid() {
        return LeaveApproverid;
    }

    public void setLeaveApproverid(Integer leaveApproverid) {
        LeaveApproverid = leaveApproverid;
    }
}

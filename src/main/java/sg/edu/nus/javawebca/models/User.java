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
    @NotNull(message = "username must be provided")
    private String username;
    @NotNull(message = "role type must be provided")
    private Integer role;//"0" = "admin" "1" = "staff" "2" = "manager"
    private String email;
    private int annual_leave_entitlement;
    private int medical_leave_entitlement;
    private int compensation_leave_balance;

    @OneToMany(mappedBy = "user")
    private List<LeaveApplication> leaveApplications;

    @OneToMany(mappedBy = "approved_by")
    private List<CompensationLeave> approvedLeaves;

    public User(){}

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
}

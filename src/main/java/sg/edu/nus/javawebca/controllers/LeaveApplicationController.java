package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.edu.nus.javawebca.interfacemethods.LeaveApplicationInterface;
import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;

@Controller
@RequestMapping("/all")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationInterface leaveApplicationinterface;

    @Autowired
    public void setLeaveApplication(LeaveApplicationInterface leaveApplication) {
        this.leaveApplicationinterface = leaveApplication;
    }

    @GetMapping("/leaveapplication")
    public String AllleaveApplication(Model model) {
        List<LeaveApplication> leaveApplications = leaveApplicationinterface.getAllLeaveApplications();
        model.addAttribute("leaveApplications", leaveApplications);
        return "leaveapplication"; // This should match the name of your HTML template without the .html extension
    }
}

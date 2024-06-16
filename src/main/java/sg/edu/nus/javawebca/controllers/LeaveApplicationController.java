package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.interfacemethods.LeaveApplicationInterface;
import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/leaveapplications")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationInterface leaveApplicationinterface;

    @Autowired
    public void setLeaveApplication(LeaveApplicationInterface leaveApplication) {
        this.leaveApplicationinterface = leaveApplication;
    }

    @GetMapping("/all")
    public String AllleaveApplication(Model model) {
        List<LeaveApplication> leaveApplications = leaveApplicationinterface.getAllLeaveApplications();
        model.addAttribute("leaveApplications", leaveApplications);
        return "leaveapplication"; // This should match the name of your HTML template without the .html extension
    }

    @GetMapping("/status/{i}")
    public List<LeaveApplication> getLeaveApplicationStatus(@PathVariable("i") int i) {
        List<LeaveApplication> res = leaveApplicationinterface.getLeaveApplicationsByStatus(i);

        return leaveApplicationinterface.getLeaveApplicationsByStatus(i);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateLeaveApplicationStatus(@PathVariable int id, @RequestBody LeaveApplication updatedApplication) {
        Optional<LeaveApplication> leaveApplicationOpt = leaveApplicationinterface.findById(id);
        if (leaveApplicationOpt.isPresent()) {
            LeaveApplication leaveApplication = leaveApplicationOpt.get();
            leaveApplication.setStatus(updatedApplication.getStatus());
            leaveApplicationinterface.saveLeaveApplication(leaveApplication);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

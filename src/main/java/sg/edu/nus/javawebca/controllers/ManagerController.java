package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.repositories.AdminRepository;
import sg.edu.nus.javawebca.services.AdminService;
import sg.edu.nus.javawebca.services.AdminServiceImpl;
import sg.edu.nus.javawebca.services.LeaveApplicationInterface;
import sg.edu.nus.javawebca.services.ManagerService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/status/{s}")
    public List<LeaveApplication> getLeaveApplicationByStatus(@PathVariable LeaveApplicationStatusEnum s){
        List<LeaveApplication> res = managerService.getLeaveApplicationsByStatus(s);
        return res;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateLeaveApplicationStatus(@PathVariable int id, @RequestBody LeaveApplication updatedApplication){
        Optional<LeaveApplication> leaveApplicationOpt = managerService.getLeaveApplicationById(id);
        if(leaveApplicationOpt.isPresent()){
            LeaveApplication leaveApplication = leaveApplicationOpt.get();
            leaveApplication.setStatus(updatedApplication.getStatus());
            managerService.saveLeaveApplication(leaveApplication);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
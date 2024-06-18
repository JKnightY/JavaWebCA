package sg.edu.nus.javawebca.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.models.LeaveType;
import sg.edu.nus.javawebca.repositories.LeaveTypeRepository;
import sg.edu.nus.javawebca.services.LeaveApplicationInterface;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.validator.LeaveApplicationValidator;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationInterface leaveApplicationinterface;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

//    @Autowired
//    private LeaveApplicationValidator leaveApplicationValidator;

//    @InitBinder("leaveApplication")
//    private void initCourseBinder(WebDataBinder binder) {
//        binder.addValidators(leaveApplicationValidator);
//    }


    @Autowired
    public void setLeaveApplication(LeaveApplicationInterface leaveApplication) {
        this.leaveApplicationinterface = leaveApplication;
    }

    @GetMapping("/leaveApplication/history")
    public String allLeaveApplication(Model model) {
        List<LeaveApplication> leaveApplications = leaveApplicationinterface.findAllLeaveApplications();
        model.addAttribute("leaveApplications", leaveApplications);
        return "leaveApplication-history";
    }

    @GetMapping("/apply-leave")
    public String showApplyLeaveForm(Model model) {
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", new LeaveApplication());
        return "apply-leave"; // The form for applying leave
    }
    @PostMapping("/apply-leave")
    public String createApplyLeave(@ModelAttribute ("leaveApplication") LeaveApplication inleaveApplication, BindingResult result, Model model) {
        inleaveApplication.setStatus(LeaveApplicationStatusEnum.APPLIED);
        inleaveApplication.setCreated_at(LocalDateTime.now());
        leaveApplicationinterface.createApplyLeave(inleaveApplication);
        return "redirect:/staff/leaveApplication/history"; // Redirect to leave application list
    }

    @GetMapping("/leaveApplication/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model) {
       LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        model.addAttribute("leaveApplication", leaveApplication);

        return "leaveApplication-edit";
    }

    @PostMapping("/leaveApplication/edit/{id}")
    public String editLeave(@ModelAttribute LeaveApplication leaveApplication, BindingResult result, @PathVariable Integer id){
        leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationinterface.updateLeaveApplication(leaveApplication);

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value="/leaveApplication/delete/{id}")
    public String deleteLeaveApplication(@PathVariable Integer id){
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.DELETED);
        leaveApplicationinterface.deleteLeaveApplication(leaveApplication);

        String message = "Leave application deleted successfully";

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value="/leaveApplication/cancel/{id}")
    public String cancelLeaveApplication(@PathVariable Integer id){
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.CANCEL);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationinterface.updateLeaveApplication(leaveApplication);

        return "redirect:/staff/leaveApplication/history";
    }
}

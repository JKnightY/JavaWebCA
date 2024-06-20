package sg.edu.nus.javawebca.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
import sg.edu.nus.javawebca.services.LeaveTypeService;
import sg.edu.nus.javawebca.validator.LeaveApplicationValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationInterface leaveApplicationinterface;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveApplicationValidator leaveApplicationValidator;

    @InitBinder("leaveApplication")
    private void initCourseBinder(WebDataBinder binder) {
        binder.addValidators(leaveApplicationValidator);
    }


    @Autowired
    public void setLeaveApplication(LeaveApplicationInterface leaveApplication) {
        this.leaveApplicationinterface = leaveApplication;
    }

    @GetMapping("/leaveApplication/history")
    public String allLeaveApplication(Model model) {
        List<LeaveApplication> leaveApplications = leaveApplicationinterface.findAllLeaveApplications();
        model.addAttribute("leaveApplications", leaveApplications);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        return "leaveApplication-history";
    }

    @GetMapping("/apply-leave")
    public String showApplyLeaveForm(Model model) {
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", new LeaveApplication());
        return "apply-leave"; // The form for applying leave
    }

    @PostMapping("/apply-leave")
    public String createApplyLeave(@ModelAttribute @Valid LeaveApplication inleaveApplication, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 即使 leaveTypes 不为空，我们仍然需要在有错误时重新加载它们，以便返回表单页面时显示
            List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "apply-leave"; // Return to form if there are errors
        }


        Optional<LeaveType> leaveTypeOptional = leaveTypeService.findLeaveTypeById(inleaveApplication.getLeaveType().getId());

        inleaveApplication.setLeaveType(leaveTypeOptional.get());
        inleaveApplication.setStatus(LeaveApplicationStatusEnum.APPLIED);
        inleaveApplication.setCreated_at(LocalDateTime.now());
        inleaveApplication.setUpdated_at(LocalDateTime.now());
        //String leavetypename = leaveTypeOptional.get().getName();
        //LeaveType leaveType = leaveTypeOptional.get();
        //System.out.println(leaveType);
        //inleaveApplication.setLeaveType(leaveType);
        leaveApplicationinterface.createApplyLeave(inleaveApplication);
        return "redirect:/staff/leaveApplication/history"; // Redirect to leave application list
    }

    @GetMapping("/leaveApplication/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model) {
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", leaveApplication);

        return "leaveApplication-edit";
    }

    @PostMapping("/leaveApplication/edit/{id}")
    public String editLeave(@ModelAttribute @Valid LeaveApplication leaveApplication, BindingResult result, @PathVariable Integer id, Model model) {
        if (result.hasErrors()) {
            List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "leaveApplication-edit"; // Return to form if there are errors
        }
        Optional<LeaveType> leaveTypeOptional = leaveTypeService.findLeaveTypeById(leaveApplication.getLeaveType().getId());

        leaveApplication.setLeaveType(leaveTypeOptional.get());

        leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationinterface.updateLeaveApplication(leaveApplication);

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value = "/leaveApplication/delete/{id}")
    public String deleteLeaveApplication(@PathVariable Integer id) {
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.DELETED);
        leaveApplicationinterface.deleteLeaveApplication(leaveApplication);

        String message = "Leave application deleted successfully";

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value = "/leaveApplication/cancel/{id}")
    public String cancelLeaveApplication(@PathVariable Integer id) {
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.CANCEL);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationinterface.updateLeaveApplication(leaveApplication);

        return "redirect:/staff/leaveApplication/history";
    }
}

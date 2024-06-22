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
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.LeaveApplicationInterface;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.services.LeaveTypeService;
import sg.edu.nus.javawebca.services.UserInterface;
import sg.edu.nus.javawebca.validator.LeaveApplicationValidator;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private UserInterface userService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(leaveApplicationValidator);
    }


    @Autowired
    public void setLeaveApplication(LeaveApplicationInterface leaveApplication) {
        this.leaveApplicationinterface = leaveApplication;
    }

    @RequestMapping("/leaveApplication/history")
    public String allLeaveApplication(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login"; // 如果没有找到用户，则重定向到登录页面
        }
        model.addAttribute("username", user.getUsername());
        List<LeaveApplication> leaveApplications = leaveApplicationinterface.findLeaveApplicationsByUserId(user.getId());
        model.addAttribute("leaveApplications", leaveApplications);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        return "/leaveApplication-history";
    }

    @GetMapping("/apply-leave")
    public String showApplyLeaveForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<LeaveType> leaveTypes;

        if (user.getDepartment() == 0) {
            leaveTypes = leaveTypeService.findLeaveTypesByIds(Arrays.asList(1, 3, 4));
        } else if (user.getDepartment() == 1) {
            leaveTypes = leaveTypeService.findLeaveTypesByIds(Arrays.asList(2, 3, 4));
        } else {
            leaveTypes = leaveTypeService.findAllLeaveTypes(); // Fallback to all types if department is unknown
        }

        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", new LeaveApplication());
        return "/apply-leave"; // The form for applying leave
    }

    @PostMapping("/apply-leave")
    public String createApplyLeave(@ModelAttribute @Valid LeaveApplication inleaveApplication, BindingResult result, Model model, HttpSession session) {
        // 调试信息：打印提交的数据
        System.out.println("Submitted Leave Application: " + inleaveApplication);

        // 打印各个字段的值，特别是 end_date
        System.out.println("End Date: " + inleaveApplication.getEnd_date());

        if (result.hasErrors()) {
            List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "/apply-leave"; // Return to form if there are errors
        }

        User user = (User) session.getAttribute("user");

        inleaveApplication.setUser(user);
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

        long daysBetween = ChronoUnit.DAYS.between(inleaveApplication.getStart_date(), inleaveApplication.getEnd_date());
        Integer intdays = (int) daysBetween + 1;

        System.out.println("days: " + intdays);

        if (inleaveApplication.getLeaveType().getId() == 1 || inleaveApplication.getLeaveType().getId() == 2) {
            user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() - intdays);
        } else if (inleaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID
            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() - intdays);
        }

        System.out.println("Before update: " + user);
        userService.updateUser(user);
        System.out.println("After update: " + user);

        return "redirect:/staff/leaveApplication/history"; // Redirect to leave application list
    }

    @GetMapping("/leaveApplication/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model) {
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", leaveApplication);

        return "/leaveApplication-edit";
    }

    @PostMapping("/leaveApplication/edit/{id}")
    public String editLeave(@ModelAttribute @Valid LeaveApplication leaveApplication, BindingResult result, @PathVariable Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "/leaveApplication-edit"; // Return to form if there are errors
        }
        User user = (User) session.getAttribute("user");
        leaveApplication.setUser(user);
        Optional<LeaveType> leaveTypeOptional = leaveTypeService.findLeaveTypeById(leaveApplication.getLeaveType().getId());

        leaveApplication.setLeaveType(leaveTypeOptional.get());

        leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationinterface.updateLeaveApplication(leaveApplication);

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value = "/leaveApplication/delete/{id}")
    public String deleteLeaveApplication(@PathVariable Integer id, HttpSession session) {
        LeaveApplication leaveApplication = leaveApplicationinterface.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.DELETED);
        leaveApplicationinterface.deleteLeaveApplication(leaveApplication);

        String message = "Leave application deleted successfully";

        User user = (User) session.getAttribute("user");

        long daysBetween = ChronoUnit.DAYS.between(leaveApplication.getStart_date(), leaveApplication.getEnd_date());
        Integer intdays = (int) daysBetween + 1;

        System.out.println("days: " + intdays);

        if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
            user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + intdays);
        } else if (leaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID
            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() + intdays);
        }

        System.out.println("Before update: " + user);
        userService.updateUser(user);
        System.out.println("After update: " + user);

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

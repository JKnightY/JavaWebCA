package sg.edu.nus.javawebca.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.*;
import sg.edu.nus.javawebca.services.*;
import sg.edu.nus.javawebca.validator.LeaveApplicationValidator;

import java.time.LocalDate;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationImpl leaveApplicationService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveApplicationValidator leaveApplicationValidator;

    @Autowired
    private UserInterface userService;

    @Autowired
    private PublicHolidayService publicHolidayService;

    @InitBinder("leaveApplication")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(leaveApplicationValidator);
    }


    @Autowired
    public void setLeaveApplication(LeaveApplicationImpl leaveApplication) {
        this.leaveApplicationService = leaveApplication;
    }

    @RequestMapping("/leaveApplication/history")
    public String allLeaveApplication(HttpSession session, Model model, @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "2") int size) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login"; // 如果没有找到用户，则重定向到登录页面
        }
        model.addAttribute("user", user);
        Pageable pageable = PageRequest.of(page, size);
        Page<LeaveApplication> leaveApplications = leaveApplicationService.findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(user.getId(), pageable);
        model.addAttribute("leaveApplications", leaveApplications);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        return "leaveApplication-history";
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
        leaveApplicationService.createApplyLeave(inleaveApplication);

        Integer intdays = leaveApplicationService.calculateTotalDays(inleaveApplication.getStart_date(), inleaveApplication.getEnd_date());

        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

        if (inleaveApplication.getLeaveType().getId() == 1 || inleaveApplication.getLeaveType().getId() == 2) {
            if (intdays <= 14) {
                Integer realintdays = leaveApplicationService.calculateWorkingDays(inleaveApplication.getStart_date(), inleaveApplication.getEnd_date(), publicHolidays);
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() - realintdays);
            } else {
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() - intdays);
            }

        }
        if (inleaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID

            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() - leaveApplicationService.calculateTotalDays(inleaveApplication.getStart_date(), inleaveApplication.getEnd_date()));

        }

        System.out.println("Before update: " + user);
        userService.updateUser(user);
        System.out.println("After update: " + user);

        return "redirect:/staff/leaveApplication/history"; // Redirect to leave application list
    }

    @GetMapping("/leaveApplication/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model, HttpSession session) {
        LeaveApplication leaveApplication = leaveApplicationService.findLeaveApplicationById(id);
        List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveApplication", leaveApplication);

        User user = (User) session.getAttribute("user");
        Integer intdays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());

        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

        if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
            if (intdays <= 14) {
                Integer realintdays = leaveApplicationService.calculateWorkingDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date(), publicHolidays);
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + realintdays);
            } else {
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + intdays);
            }

        }
        if (leaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID

            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() + intdays);

        }
        userService.updateUser(user);
        return "/leaveApplication-edit";
    }

    @PostMapping("/leaveApplication/edit/{id}")
    public String editLeave(@ModelAttribute @Valid LeaveApplication leaveApplication, BindingResult result, @PathVariable Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<LeaveType> leaveTypes = leaveTypeService.findAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "/leaveApplication-edit"; // Return to form if there are errors
        }
        LeaveApplication originalLeaveApplication = leaveApplicationService.findLeaveApplicationById(id);
        leaveApplication.setCreated_at(originalLeaveApplication.getCreated_at());

        User user = (User) session.getAttribute("user");
        leaveApplication.setUser(user);
        Optional<LeaveType> leaveTypeOptional = leaveTypeService.findLeaveTypeById(leaveApplication.getLeaveType().getId());

        leaveApplication.setLeaveType(leaveTypeOptional.get());

        leaveApplication.setStatus(LeaveApplicationStatusEnum.UPDATED);
        leaveApplication.setUpdated_at(LocalDateTime.now());


        Integer intdays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());

        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

        if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
            if (intdays <= 14) {
                Integer realintdays = leaveApplicationService.calculateWorkingDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date(), publicHolidays);
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() - realintdays);
            } else {
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() - intdays);
            }

        }
        if (leaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID

            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() - leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date()));

        }
        leaveApplicationService.updateLeaveApplication(leaveApplication);
        userService.updateUser(user);
        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value = "/leaveApplication/delete/{id}")
    public String deleteLeaveApplication(@PathVariable Integer id, HttpSession session) {
        LeaveApplication leaveApplication = leaveApplicationService.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.DELETED);
        leaveApplicationService.deleteLeaveApplication(leaveApplication);

        String message = "Leave application deleted successfully";

        User user = (User) session.getAttribute("user");

        Integer intdays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());

        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

        if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
            if (intdays <= 14) {
                Integer realintdays = leaveApplicationService.calculateWorkingDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date(), publicHolidays);
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + realintdays);
            } else {
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + intdays);
            }

        }
        if (leaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID

            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() + intdays);

        }

        System.out.println("Before update: " + user);
        userService.updateUser(user);
        System.out.println("After update: " + user);

        return "redirect:/staff/leaveApplication/history";
    }

    @RequestMapping(value = "/leaveApplication/cancel/{id}")
    public String cancelLeaveApplication(@PathVariable Integer id, HttpSession session) {
        LeaveApplication leaveApplication = leaveApplicationService.findLeaveApplicationById(id);
        leaveApplication.setStatus(LeaveApplicationStatusEnum.CANCEL);
        leaveApplication.setUpdated_at(LocalDateTime.now());
        leaveApplicationService.updateLeaveApplication(leaveApplication);


        User user = (User) session.getAttribute("user");

        Integer intdays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());

        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();

        if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
            if (intdays <= 14) {
                Integer realintdays = leaveApplicationService.calculateWorkingDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date(), publicHolidays);
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + realintdays);
            } else {
                user.setAnnual_leave_entitlement_last(user.getAnnual_leave_entitlement_last() + intdays);
            }

        }
        if (leaveApplication.getLeaveType().getId() == 3) { // Medical leave type ID

            user.setMedical_leave_entitlement_last(user.getMedical_leave_entitlement_last() + intdays);

        }
        return "redirect:/staff/leaveApplication/history";
    }
}

package sg.edu.nus.javawebca.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.CompensationLeaveInterface;
import sg.edu.nus.javawebca.services.UserInterface;
import sg.edu.nus.javawebca.services.UserInterfaceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff")
public class CompensationLeaveController {
    @Autowired
    private CompensationLeaveInterface compensationLeaveInterface;

    @Autowired
    private UserInterfaceImpl userInterface;

    @Autowired
    public void setCompensationLeave(CompensationLeaveInterface compensationLeave) {
        this.compensationLeaveInterface = compensationLeave;
    }


    @GetMapping("/compensationLeave/history")
    public String allCompensationLeave(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // 如果没有找到用户，则重定向到登录页面
        }
        List<CompensationLeave> compensationLeaves = compensationLeaveInterface.findAllCompensationLeavesByUser(user);
        model.addAttribute("compensationLeaves", compensationLeaves);
        return "compensationLeave-history";
    }

    @GetMapping("/apply-comLeave")
    public String showCompensationLeaveForm(HttpSession session, Model model) {
        model.addAttribute("compensationLeave", new CompensationLeave());
        User user = (User) session.getAttribute("user");
//        double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());
        double total=user.getCompensation_leave_balance_last();
        System.out.println(total);
        model.addAttribute("total", total);
        return "apply-comLeave"; // The form for applying leave
    }

    @PostMapping("/apply-comLeave")
    public String createCompensationLeave(@ModelAttribute("compensationLeave") @Valid CompensationLeave compensationLeave, BindingResult bindingResult, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (compensationLeave.getEndDate().isBefore(compensationLeave.getStartDate()) ||
                (compensationLeave.getEndDate().isEqual(compensationLeave.getStartDate()) &&
                        "MORNING".equals(compensationLeave.getEndPeriod()) &&
                        "AFTERNOON".equals(compensationLeave.getStartPeriod()))) {
            bindingResult.rejectValue("endDate", "error.compensationLeave", "End date and time cannot be earlier than start date and time.");
        }

        // Fetch historical leaves ending after the new leave's start date
        List<CompensationLeave> historyLeaves = compensationLeaveInterface.findLeavesEndingAfter(user.getId(),compensationLeave.getStartDate());
        if (!historyLeaves.isEmpty()) {
            System.out.println("wrong");
            bindingResult.rejectValue("startDate", "error.compensationLeave", "There is a historical leave that conflicts with the new leave's start date.");
        }

        double total2 = compensationLeaveInterface.calculateCompensationLeave(user.getId());
        if (compensationLeave.getLeave_days() > total2 + (compensationLeave.getHours_worked() / 4) / 2) {
            bindingResult.rejectValue("endDate", "error.compensationLeaveDays", "Insufficient compensatory leave days available.");
        }



        if (bindingResult.hasErrors()) {
            System.out.println("Wow");
            return "apply-comLeave";
        }


        compensationLeave.setUser(user);
        compensationLeaveInterface.createCompensationLeave(compensationLeave);
        double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());
        user.setCompensation_leave_balance_last(total);
        userInterface.updateUser(user);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.APPLIED);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.updateCompensationLeave(compensationLeave);
        return "redirect:/staff/compensationLeave/history"; // Redirect to leave application list
    }


    @GetMapping("/compensationLeave/edit/{id}")
    public String editLeavePage(HttpSession session,@PathVariable Integer id, Model model) {
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        model.addAttribute("compensationLeave", compensationLeave);
        User user = (User) session.getAttribute("user");
        double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());
        user.setCompensation_leave_balance_last(total);
        userInterface.updateUser(user);
        model.addAttribute("total", total);
        return "compensationLeave-edit";
    }

    @PostMapping("/compensationLeave/edit/{id}")
    public String editCourse(HttpSession session,@ModelAttribute CompensationLeave compensationLeave, BindingResult bindingResult, @PathVariable Integer id){
        User user = (User) session.getAttribute("user");

        if (compensationLeave.getEndDate().isBefore(compensationLeave.getStartDate()) ||
                (compensationLeave.getEndDate().isEqual(compensationLeave.getStartDate()) &&
                        "MORNING".equals(compensationLeave.getEndPeriod()) &&
                        "AFTERNOON".equals(compensationLeave.getStartPeriod()))) {
            bindingResult.rejectValue("endDate", "error.compensationLeave", "End date and time cannot be earlier than start date and time.");
        }

        List<CompensationLeave> historyLeaves = compensationLeaveInterface.findLeavesEndingAfter(user.getId(),compensationLeave.getStartDate());
        if (!historyLeaves.isEmpty()) {
            bindingResult.rejectValue("startDate", "error.compensationLeave", "There is a historical leave that conflicts with the new leave's start date.");
        }

        double total2 = compensationLeaveInterface.calculateCompensationLeave(user.getId());
        if (compensationLeave.getLeave_days() > total2 + (compensationLeave.getHours_worked() / 4) / 2) {
            bindingResult.rejectValue("endDate", "error.compensationLeaveDays", "Insufficient compensatory leave days available.");
        }



        if (bindingResult.hasErrors()) {
            return "apply-comLeave";
        }


        compensationLeave.setUser(user);
        compensationLeaveInterface.createCompensationLeave(compensationLeave);
        double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());
        user.setCompensation_leave_balance_last(total);
        userInterface.updateUser(user);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.APPLIED);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.updateCompensationLeave(compensationLeave);

        return "redirect:/staff/compensationLeave/history";
    }

    @RequestMapping(value="/compensationLeave/delete/{id}")
    public String deleteCompensationLeave(HttpSession session,@PathVariable Integer id){
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.DELETED);
        compensationLeaveInterface.deleteCompensationLeave(compensationLeave);

        User user = (User) session.getAttribute("user");

        double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());

        user.setCompensation_leave_balance_last(total);

        userInterface.updateUser(user);

//        if (compensationLeave != null && compensationLeave.getStatus() == LeaveApplicationStatusEnum.APPLIED) {
//            compensationLeave.setStatus(LeaveApplicationStatusEnum.DELETED);
//            compensationLeaveInterface.deleteCompensationLeave(compensationLeave);
//            double total=compensationLeaveInterface.calculateCompensationLeave(user.getId());
//            user.setCompensation_leave_balance_last(total);
//        }
        return "redirect:/staff/compensationLeave/history";
    }

    @RequestMapping(value="/compensationLeave/cancel/{id}")
    public String cancelCompensationLeave(HttpSession session,@PathVariable Integer id){
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.CANCEL);
        compensationLeaveInterface.updateCompensationLeave(compensationLeave);

        User user = (User) session.getAttribute("user");
        List<CompensationLeave> histories = compensationLeaveInterface.findAllCompensationLeavesByUser(user);

        histories = histories.stream()
                .filter(history -> history.getId()!=id)
                .collect(Collectors.toList());

        double totalHoursWorked = histories.stream().mapToDouble(CompensationLeave::getHours_worked).sum();
        double totalLeaveDays = histories.stream().mapToDouble(CompensationLeave::getLeave_days).sum();
        double v = (totalHoursWorked / 4) / 2 - totalLeaveDays;
        user.setCompensation_leave_balance_last(v);
        userInterface.updateUser(user);
        return "redirect:/staff/compensationLeave/history";

    }
}
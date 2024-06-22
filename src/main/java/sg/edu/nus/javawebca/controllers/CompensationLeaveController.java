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

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class CompensationLeaveController {
    @Autowired
    private CompensationLeaveInterface compensationLeaveInterface;

    @Autowired
    public void setCompensationLeave(CompensationLeaveInterface compensationLeave) {
        this.compensationLeaveInterface = compensationLeave;
    }


    @GetMapping("/compensationLeave/history")
    public String allCompensationLeave(Model model) {
        List<CompensationLeave> compensationLeaves = compensationLeaveInterface.findAllCompensationLeaves();
        model.addAttribute("compensationLeaves", compensationLeaves);
        return "compensationLeave-history";
    }

    @GetMapping("/apply-comLeave")
    public String showCompensationLeaveForm(HttpSession session, Model model) {
        model.addAttribute("compensationLeave", new CompensationLeave());
        User user = (User) session.getAttribute("user");
        int userId=user.getId();
        System.out.println(userId);
        double total=compensationLeaveInterface.calculateCompensationLeave(userId);
        System.out.println(total);
        model.addAttribute("total", total);
        return "apply-comLeave"; // The form for applying leave
    }

    @PostMapping("/apply-comLeave")
    public String createCompensationLeave(@ModelAttribute("compensationLeave") @Valid CompensationLeave compensationLeave, BindingResult bindingResult, Model model, HttpSession session) {
        if (compensationLeave.getEndDate().isBefore(compensationLeave.getStartDate()) ||
                (compensationLeave.getEndDate().isEqual(compensationLeave.getStartDate()) &&
                        "MORNING".equals(compensationLeave.getEndPeriod()) &&
                        "AFTERNOON".equals(compensationLeave.getStartPeriod()))) {
            bindingResult.rejectValue("endDate", "error.compensationLeave", "End date and time cannot be earlier than start date and time.");
        }

        // Fetch historical leaves ending after the new leave's start date
        List<CompensationLeave> historyLeaves = compensationLeaveInterface.findLeavesEndingAfter(compensationLeave.getStartDate());
        if (!historyLeaves.isEmpty()) {
            System.out.println("wrong");
            bindingResult.rejectValue("startDate", "error.compensationLeave", "There is a historical leave that conflicts with the new leave's start date.");
        }


        if (bindingResult.hasErrors()) {
            System.out.println("Wow");
            return "apply-comLeave";
        }

        User user = (User) session.getAttribute("user");

        compensationLeave.setUser(user);

        compensationLeave.setStatus(LeaveApplicationStatusEnum.APPLIED);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.createCompensationLeave(compensationLeave);


        System.out.println("right");
        return "redirect:/staff/compensationLeave/history"; // Redirect to leave application list
    }


    @GetMapping("/compensationLeave/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model) {
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.UPDATED);
        compensationLeave.setUpdate_at(LocalDateTime.now());
        compensationLeaveInterface.deleteCompensationLeave(compensationLeave);
        model.addAttribute("compensationLeave", compensationLeave);
        return "compensationLeave-edit";
    }

//    @PostMapping("/compensationLeave/edit/{id}")
//    public String editCourse(@ModelAttribute CompensationLeave compensationLeave, BindingResult result, @PathVariable Integer id){
//        compensationLeave.setStatus(LeaveApplicationStatusEnum.UPDATED);
//        compensationLeave.setUpdate_at(LocalDateTime.now());
//        compensationLeaveInterface.updateCompensationLeave(compensationLeave);
//
////        // Create and save CompensationLeaveHistory for update
////        CompensationLeaveHistory compensationLeaveHistory = new CompensationLeaveHistory();
////        compensationLeaveHistory.setCompensationLeave(compensationLeave);
////        compensationLeaveHistory.setStartDate(compensationLeave.getStartDate());
////        compensationLeaveHistory.setEndDate(compensationLeave.getEndDate());
////        compensationLeaveHistory.setStartPeriod(compensationLeave.getStartPeriod());
////        compensationLeaveHistory.setEndPeriod(compensationLeave.getEndPeriod());
////        compensationLeaveHistoryService.save(compensationLeaveHistory);
//
//        return "redirect:/staff/compensationLeave/history";
//    }

    @RequestMapping(value="/compensationLeave/delete/{id}")
    public String deleteCompensationLeave(@PathVariable Integer id){
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        if(compensationLeave.getStatus()==LeaveApplicationStatusEnum.APPLIED){
        compensationLeave.setStatus(LeaveApplicationStatusEnum.DELETED);
        compensationLeaveInterface.deleteCompensationLeave(compensationLeave);
        }

        String message = "Leave application deleted successfully";

        return "redirect:/staff/compensationLeave/history";
    }

    @RequestMapping(value="/compensationLeave/cancel/{id}")
    public String cancelCompensationLeave(@PathVariable Integer id){
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.CANCEL);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.updateCompensationLeave(compensationLeave);

        return "redirect:/staff/compensationLeave/history";
    }
}
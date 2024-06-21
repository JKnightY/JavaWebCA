package sg.edu.nus.javawebca.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.services.CompensationLeaveInterface;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class CompensationLeaveController {
    @Autowired
    private CompensationLeaveInterface compensationLeaveInterface;

    @Autowired
    private CompensationLeaveHistoryService compensationLeaveHistoryService;

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
    public String showCompensationLeaveForm(Model model) {
        model.addAttribute("compensationLeave", new CompensationLeave());
        return "apply-comLeave"; // The form for applying leave
    }

    @PostMapping("/apply-comLeave")
    public String createCompensationLeave(@Valid CompensationLeave compensationLeave, BindingResult bindingResult, Model model) {
        if (compensationLeave.getEndDate().isBefore(compensationLeave.getStartDate()) ||
                (compensationLeave.getEndDate().isEqual(compensationLeave.getStartDate()) &&
                        "MORNING".equals(compensationLeave.getEndPeriod()) &&
                        "AFTERNOON".equals(compensationLeave.getStartPeriod()))) {
            bindingResult.rejectValue("endDate", "error.compensationLeave", "End date and time cannot be earlier than start date and time.");
        }

        // Fetch historical leaves ending after the new leave's start date
        List<CompensationLeaveHistory> historyLeaves = compensationLeaveHistoryService.findLeavesEndingAfter(compensationLeave.getStartDate());
        if (!historyLeaves.isEmpty()) {
            System.out.println("wrong");
            bindingResult.rejectValue("startDate", "error.compensationLeave", "There is a historical leave that conflicts with the new leave's start date.");
        }


        if (bindingResult.hasErrors()) {
            return "apply-comLeave";
        }
        compensationLeave.setStatus(LeaveApplicationStatusEnum.APPLIED);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.createCompensationLeave(compensationLeave);
        System.out.println("right");
        return "redirect:/staff/compensationLeave/history"; // Redirect to leave application list
    }


    @GetMapping("/compensationLeave/edit/{id}")
    public String editLeavePage(@PathVariable Integer id, Model model) {
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        model.addAttribute("compensationLeave", compensationLeave);

        return "compensationLeave-edit";
    }

    @PostMapping("/compensationLeave/edit/{id}")
    public String editCourse(@ModelAttribute CompensationLeave compensationLeave, BindingResult result, @PathVariable Integer id){
        compensationLeave.setStatus(LeaveApplicationStatusEnum.UPDATED);
        compensationLeave.setUpdate_at(LocalDateTime.now());
        compensationLeaveInterface.updateCompensationLeave(compensationLeave);
        return "redirect:/staff/compensationLeave/history";
    }

    @RequestMapping(value="/compensationLeave/delete/{id}")
    public String deleteCompensationLeave(@PathVariable Integer id){
        CompensationLeave compensationLeave = compensationLeaveInterface.findCompensationLeaveById(id);
        compensationLeave.setStatus(LeaveApplicationStatusEnum.DELETED);
        compensationLeaveInterface.deleteCompensationLeave(compensationLeave);

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
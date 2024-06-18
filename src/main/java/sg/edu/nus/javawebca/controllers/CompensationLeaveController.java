package sg.edu.nus.javawebca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.services.CompensationLeaveInterface;
import sg.edu.nus.javawebca.services.LeaveApplicationInterface;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class CompensationLeaveController {
    @Autowired
    private CompensationLeaveInterface compensationLeaveInterface;

//    @Autowired
//    private LeaveApplicationValidator leaveApplicationValidator;

//    @InitBinder("leaveApplication")
//    private void initCourseBinder(WebDataBinder binder) {
//        binder.addValidators(leaveApplicationValidator);
//    }


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
    public String createApplyCompensationLeave(@ModelAttribute ("compensationLeave") CompensationLeave compensationLeave, BindingResult result, Model model) {
        compensationLeave.setStatus(LeaveApplicationStatusEnum.APPLIED);
        compensationLeave.setCreate_at(LocalDateTime.now());
        compensationLeaveInterface.createCompensationLeave(compensationLeave);
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

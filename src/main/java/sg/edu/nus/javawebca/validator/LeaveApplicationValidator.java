package sg.edu.nus.javawebca.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.PublicHoliday;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.services.LeaveApplicationImpl;
import sg.edu.nus.javawebca.services.PublicHolidayService;

import java.util.List;
import java.util.Objects;

@Component
public class LeaveApplicationValidator implements Validator {
    @Autowired
    private LeaveApplicationImpl leaveApplicationService;

    @Autowired
    private PublicHolidayService publicHolidayService;


    @Override
    public boolean supports(Class<?> clazz) {
        return LeaveApplication.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {
        LeaveApplication leaveApplication = (LeaveApplication) target;

        // 验证必填项
        if (leaveApplication.getReason() == null || leaveApplication.getReason().isEmpty()) {
            errors.rejectValue("reason", "reason.empty", "Reason is required.");
        }

        if (leaveApplication.getLeaveType() == null) {
            errors.rejectValue("leaveType", "leaveType.empty", "Leave type is required.");
        }

        if (leaveApplication.getStart_date() == null) {
            errors.rejectValue("start_date", "start_date.empty", "Start date is required.");
        }

        if (leaveApplication.getEnd_date() == null) {
            errors.rejectValue("end_date", "end_date.empty", "End date is required.");
        }

        if (leaveApplication.getStart_date() != null && leaveApplication.getEnd_date() != null) {
            if (leaveApplication.getStart_date().isAfter(leaveApplication.getEnd_date())) {
                errors.rejectValue("start_date", "start_date.invalid", "Start date must be before end date.");
            }
        }

        // 检查开始日期和结束日期是否为工作日
        if (leaveApplication.getStart_date() != null) {
            if (leaveApplicationService.isNonWorkingDay(leaveApplication.getStart_date())) {
                errors.rejectValue("start_date", "start_date.invalid", "Start date must be a working day.");
            }
        }

        if (leaveApplication.getEnd_date() != null) {
            if (leaveApplicationService.isNonWorkingDay(leaveApplication.getEnd_date())) {
                errors.rejectValue("end_date", "end_date.invalid", "End date must be a working day.");
            }
        }

        if (leaveApplication.getLeaveType() != null && leaveApplication.getStart_date() != null && leaveApplication.getEnd_date() != null) {
            User user = leaveApplication.getUser();
            long requestedDays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());

            if (Objects.equals(leaveApplication.getLeaveType().getName(), "AnnualLeave")) {
                boolean isEligible = leaveApplicationService.isAnnualLeaveEligible(leaveApplication, publicHolidayService.getAllPublicHolidays());
                if (!isEligible) {
                    errors.rejectValue("leaveType", "leaveType.invalid", "Annual leave calculation is incorrect.");
                } else if (requestedDays > user.getAnnual_leave_entitlement_last()) {
                    errors.rejectValue("leaveType", "leaveType.insufficient", "Insufficient annual leave balance.");
                }
            } else if (leaveApplication.getLeaveType().getName() == "MedicalLeave") {
                boolean isEligible = leaveApplicationService.isMedicalLeaveEligible(leaveApplication, user.getMedical_leave_entitlement_last());
                if (!isEligible) {
                    errors.rejectValue("leaveType", "leaveType.invalid", "Medical leave exceeds the allowed limit.");
                } else if (requestedDays > user.getMedical_leave_entitlement_last()) {
                    errors.rejectValue("leaveType", "leaveType.insufficient", "Insufficient medical leave balance.");
                }

            }
        }
    }
}

package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.LeaveApplicationStatusEnum;
import sg.edu.nus.javawebca.models.PublicHoliday;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveApplicationImpl implements LeaveApplicationInterface {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private PublicHolidayService publicHolidayService;

    @Override
    @Transactional
    public List<LeaveApplication> findAllLeaveApplications() {
        return leaveApplicationRepository.findAll();
    }

    @Override
    @Transactional
    public List<LeaveApplication> findLeaveApplicationsByUserId(int userId){
        return leaveApplicationRepository.findLeaveApplicationsByUserId(userId);
    }

    @Override
    @Transactional
    public LeaveApplication createApplyLeave(LeaveApplication leaveApplication) {
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication){
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public void deleteLeaveApplication(LeaveApplication leaveApplication){
        leaveApplicationRepository.delete(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication findLeaveApplicationById(int id) {
        return leaveApplicationRepository.findById(id).orElse(null);
    }

    public boolean isAnnualLeaveEligible(LeaveApplication leaveApplication, List<PublicHoliday> publicHolidays) {
        LocalDate startDate = leaveApplication.getStart_date();
        LocalDate endDate = leaveApplication.getEnd_date();

        if (isNonWorkingDay(startDate) || isNonWorkingDay(endDate)) {
            return false;
        }

        long totalDays = calculateTotalDays(startDate, endDate);
        long workingDays = calculateWorkingDays(startDate, endDate, publicHolidays);

        if (totalDays <= 14) {
            // 小于等于14天，排除周末和公共假期
            return workingDays <= 14;
        } else {
            // 超过14天，包括周末和公共假期
            return totalDays > 14;
        }
    }

    public boolean isNonWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        // 检查是否为公共假日
        List<PublicHoliday> publicHolidays = publicHolidayService.getAllPublicHolidays();
        for (PublicHoliday holiday : publicHolidays) {
            if (holiday.getHoliday_date().equals(date)) {
                return true;
            }
        }
        return false;
    }

   public long calculateTotalDays(LocalDate start, LocalDate end) {
        return start.datesUntil(end.plusDays(1)).count();
    }

    private long calculateWorkingDays(LocalDate start, LocalDate end, List<PublicHoliday> publicHolidays) {
        Set<LocalDate> holidays = publicHolidays.stream()
                .map(PublicHoliday::getHoliday_date)
                .collect(Collectors.toSet());

        return start.datesUntil(end.plusDays(1))
                .filter(date -> !isNonWorkingDay(date) && !holidays.contains(date))
                .count();
    }

    public boolean isMedicalLeaveEligible(LeaveApplication leaveApplication, int currentYearTotalMedicalLeaveDays) {
        long requestedDays = calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());
        return currentYearTotalMedicalLeaveDays + requestedDays <= 60;
    }

    public boolean datesOverlap(LeaveApplication newLeave, LeaveApplication existingLeave) {
        return !newLeave.getEnd_date().isBefore(existingLeave.getStart_date()) && !newLeave.getStart_date().isAfter(existingLeave.getEnd_date());
    }


}

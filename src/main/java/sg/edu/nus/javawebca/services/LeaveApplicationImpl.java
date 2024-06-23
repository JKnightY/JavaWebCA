package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.models.PublicHoliday;
import sg.edu.nus.javawebca.repositories.LeaveApplicationRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    public Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(int userId, Pageable pageable) {
        return leaveApplicationRepository.findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(userId, pageable);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationsByUserId(int userId) {
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
        return true;
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

    public int calculateTotalDays(LocalDate start, LocalDate end) {
        // Use Math.toIntExact to safely convert long to int
        return Math.toIntExact(start.datesUntil(end.plusDays(1)).count());
    }

    public int calculateWorkingDays(LocalDate start, LocalDate end, List<PublicHoliday> publicHolidays) {
        Set<LocalDate> holidays = publicHolidays.stream()
                .map(PublicHoliday::getHoliday_date)
                .collect(Collectors.toSet());

        long workingDaysCount = start.datesUntil(end.plusDays(1))
                .filter(date -> !isNonWorkingDay(date) && !holidays.contains(date))
                .count();

        // Use Math.toIntExact to safely convert long to int
        return Math.toIntExact(workingDaysCount);
    }

    public boolean datesOverlap(LeaveApplication newLeave, LeaveApplication existingLeave) {
        return !newLeave.getEnd_date().isBefore(existingLeave.getStart_date()) && !newLeave.getStart_date().isAfter(existingLeave.getEnd_date());
    }


}

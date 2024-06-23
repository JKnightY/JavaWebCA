package sg.edu.nus.javawebca.services;

import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.User;

import java.time.LocalDate;
import java.util.List;

public interface CompensationLeaveInterface {

    List<CompensationLeave> findAllCompensationLeaves();

    CompensationLeave findCompensationLeaveById(Integer id);

    CompensationLeave createCompensationLeave(CompensationLeave compensationLeave);

    CompensationLeave updateCompensationLeave(CompensationLeave compensationLeave);

    void deleteCompensationLeave(CompensationLeave compensationLeave);

    List<CompensationLeave> findLeavesEndingAfter(Integer userId,LocalDate startDate);

    double calculateCompensationLeave(Integer userId);

    List<CompensationLeave> findAllCompensationLeavesByUser(User user);

}

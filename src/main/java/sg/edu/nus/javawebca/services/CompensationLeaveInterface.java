package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.LeaveApplication;

import java.util.List;

public interface CompensationLeaveInterface {

    List<CompensationLeave> findAllCompensationLeaves();

    CompensationLeave findCompensationLeaveById(Integer id);

    CompensationLeave createCompensationLeave(CompensationLeave compensationLeave);

    CompensationLeave updateCompensationLeave(CompensationLeave compensationLeave);

    void deleteCompensationLeave(CompensationLeave compensationLeave);
}

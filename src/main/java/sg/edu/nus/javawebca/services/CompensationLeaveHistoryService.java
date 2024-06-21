package sg.edu.nus.javawebca.services;

import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.CompensationLeaveHistory;

import java.time.LocalDate;
import java.util.List;


public interface CompensationLeaveHistoryService {
    List<CompensationLeaveHistory> findLeavesEndingAfter(LocalDate startDate);
    void save(CompensationLeaveHistory compensationLeaveHistory);
}

package sg.edu.nus.javawebca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.CompensationLeaveHistory;
import sg.edu.nus.javawebca.repositories.CompensationLeaveHistoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompensationLeaveHistoryService {

    @Autowired
    private CompensationLeaveHistoryRepository compensationLeaveHistoryRepository;

    public List<CompensationLeaveHistory> findLeavesEndingAfter(LocalDate startDate) {
        return compensationLeaveHistoryRepository.findLeavesEndingAfter(startDate);
    }
}
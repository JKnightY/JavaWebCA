package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.javawebca.models.CompensationLeaveHistory;

import java.time.LocalDate;
import java.util.List;

public interface CompensationLeaveHistoryRepository extends JpaRepository<CompensationLeaveHistory, Integer> {

    @Query("SELECT h FROM CompensationLeaveHistory h WHERE h.endDate >= :startDate")
    List<CompensationLeaveHistory> findLeavesEndingAfter(@Param("startDate") LocalDate startDate);
}

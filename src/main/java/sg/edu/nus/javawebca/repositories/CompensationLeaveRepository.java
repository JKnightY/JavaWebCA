package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.User;

import java.time.LocalDate;
import java.util.List;

public interface CompensationLeaveRepository extends JpaRepository<CompensationLeave, Integer> {
    @Query("SELECT h FROM CompensationLeave h WHERE h.endDate >= :startDate")
    List<CompensationLeave> findLeavesEndingAfter(@Param("startDate") LocalDate startDate);

    List<CompensationLeave> findByEndDateAfter(LocalDate startDate);

    List<CompensationLeave> findAllByUser(User user);
}

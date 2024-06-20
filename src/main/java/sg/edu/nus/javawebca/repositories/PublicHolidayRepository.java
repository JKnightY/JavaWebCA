package sg.edu.nus.javawebca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.javawebca.models.PublicHoliday;

import java.time.LocalDate;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Integer> {
}

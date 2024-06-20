package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.PublicHoliday;
import sg.edu.nus.javawebca.repositories.PublicHolidayRepository;

import java.util.List;

@Service
@Transactional
public class PublicHolidayService {
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Transactional
    public List<PublicHoliday> getAllPublicHolidays(){
        return publicHolidayRepository.findAll();}
}

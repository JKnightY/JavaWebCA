package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.User;
import sg.edu.nus.javawebca.repositories.CompensationLeaveRepository;
import sg.edu.nus.javawebca.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CompensationLeaveImpl implements CompensationLeaveInterface {
    @Autowired
    private CompensationLeaveRepository compensationLeaveRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<CompensationLeave> findAllCompensationLeaves() {
        return compensationLeaveRepository.findAll();
    }

    @Override
    @Transactional
    public CompensationLeave findCompensationLeaveById(Integer id) {
        return compensationLeaveRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public CompensationLeave createCompensationLeave(CompensationLeave compensationLeave) {
        return compensationLeaveRepository.save(compensationLeave);
    }

    @Override
    @Transactional
    public CompensationLeave updateCompensationLeave(CompensationLeave compensationLeave) {
        return compensationLeaveRepository.save(compensationLeave);
    }

    @Override
    @Transactional
    public void deleteCompensationLeave(CompensationLeave compensationLeave) {
        compensationLeaveRepository.delete(compensationLeave);
    }

    @Override
    public List<CompensationLeave> findLeavesEndingAfter(LocalDate startDate) {
        return compensationLeaveRepository.findLeavesEndingAfter(startDate);
    }



    public double calculateCompensationLeave(int userid) {
        User user = userRepository.findById(userid).get();
        System.out.println(user);
        List<CompensationLeave> histories = compensationLeaveRepository.findAllByUser(user);
        System.out.println(histories.size());

        double totalHoursWorked = histories.stream().mapToDouble(CompensationLeave::getHours_worked).sum();
        System.out.println(totalHoursWorked);
        double totalLeaveDays = histories.stream().mapToDouble(CompensationLeave::getLeave_days).sum();
        System.out.println(totalLeaveDays);
        double v = (totalHoursWorked / 4) / 2 - totalLeaveDays;
        return v;
    }




}

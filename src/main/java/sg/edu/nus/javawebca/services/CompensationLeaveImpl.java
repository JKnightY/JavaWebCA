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
    @Transactional
    public List<CompensationLeave> findLeavesEndingAfter(Integer userId,LocalDate startDate) {
        return compensationLeaveRepository.findLeavesEndingAfter(userId,startDate);
    }

    @Override
    @Transactional
    public List<CompensationLeave> findAllCompensationLeavesByUser(User user){
        return compensationLeaveRepository.findAllByUser(user);
    }


    @Override
    @Transactional
    public double calculateCompensationLeave(Integer userId) {

        User user = userRepository.findById(userId).get(); // 假设你已经有 userService

        List<CompensationLeave> histories = compensationLeaveRepository.findAllByUser(user);
        System.out.println("historiessize:"+histories.size());

        double totalHoursWorked = histories.stream().mapToDouble(CompensationLeave::getHours_worked).sum();
        System.out.println("totalhoursworked"+totalHoursWorked);
        double totalLeaveDays = histories.stream().mapToDouble(CompensationLeave::getLeave_days).sum();
        System.out.println("totalleavedays"+totalLeaveDays);
        double v = (totalHoursWorked / 4) / 2 - totalLeaveDays;
        return v;
    }




}

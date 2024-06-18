package sg.edu.nus.javawebca.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.javawebca.models.CompensationLeave;
import sg.edu.nus.javawebca.models.LeaveApplication;
import sg.edu.nus.javawebca.repositories.CompensationLeaveRepository;

import java.util.List;

@Service
@Transactional
public class CompensationLeaveImpl implements CompensationLeaveInterface {
    @Autowired
    private CompensationLeaveRepository compensationLeaveRepository;

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


}

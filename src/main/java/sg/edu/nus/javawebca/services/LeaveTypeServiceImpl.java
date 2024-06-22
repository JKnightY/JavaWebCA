package sg.edu.nus.javawebca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.javawebca.models.LeaveType;
import sg.edu.nus.javawebca.repositories.LeaveTypeRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class LeaveTypeServiceImpl implements LeaveTypeService {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public List<LeaveType> findAllLeaveTypes(){
        return leaveTypeRepository.findAll();
    }

    @Override
    @Transactional
    public LeaveType createLeaveType(LeaveType leaveType){
        return leaveTypeRepository.save(leaveType);
    }

    @Override
    @Transactional
    public void deleteLeaveType(LeaveType leaveType) {
        leaveTypeRepository.delete(leaveType);
    }

    @Override
    public Optional<LeaveType> findLeaveTypeById(int id) {
        return leaveTypeRepository.findById(id);
    }

    @Override
    @Transactional
    public LeaveType updateLeaveType(LeaveType leaveType) {
        return leaveTypeRepository.save(leaveType);
    }

    @Override
    public List<LeaveType> findLeaveTypesByIds(List<Integer> ids) {
        return leaveTypeRepository.findAllById(ids);
    }
}

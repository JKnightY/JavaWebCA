package sg.edu.nus.javawebca.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.edu.nus.javawebca.models.LeaveApplication;

@Component
public class LeaveApplicationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LeaveApplication.class.isAssignableFrom(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

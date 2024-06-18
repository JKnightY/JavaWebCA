package sg.edu.nus.javawebca.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.edu.nus.javawebca.models.CompensationLeave;

@Component
public class CompensationLeaveValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CompensationLeave.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompensationLeave compensationLeave = (CompensationLeave) target;

        // 这里可以添加自定义验证逻辑
        if (compensationLeave.getClaim_date() == null) {
            errors.rejectValue("claim_date", "claim_date.empty", "Claim Date is required");
        }
        // 其他验证逻辑...
    }
}

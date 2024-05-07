package mate.academy.mybookshop.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRegistrationRequestDto userDto = (UserRegistrationRequestDto) value;
        String password = userDto.getPassword();
        String confirmPassword = userDto.getConfirmPassword();

        return password != null && password.equals(confirmPassword);
    }
}

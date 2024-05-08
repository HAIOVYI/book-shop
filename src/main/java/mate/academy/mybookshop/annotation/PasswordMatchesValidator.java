package mate.academy.mybookshop.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import mate.academy.mybookshop.dto.UserRegistrationRequestDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        //ignored
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof UserRegistrationRequestDto userDto) {
            return Objects.equals(userDto.getPassword(), userDto.getConfirmPassword());
        }
        return false;
    }
}

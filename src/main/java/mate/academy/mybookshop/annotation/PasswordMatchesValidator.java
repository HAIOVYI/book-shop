package mate.academy.mybookshop.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import mate.academy.mybookshop.dto.registration.UserRegistrationRequestDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof UserRegistrationRequestDto userDto) {
            return Objects.equals(userDto.getPassword(), userDto.getConfirmPassword());
        }
        return false;
    }
}

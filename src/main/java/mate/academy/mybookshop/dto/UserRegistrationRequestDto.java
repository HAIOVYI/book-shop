package mate.academy.mybookshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mate.academy.mybookshop.annotation.FieldMatch;

@FieldMatch.List({
        @FieldMatch(first = "password",
                second = "confirmPassword",
                message = "Passwords do not match")
})
@Data
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 25)
    private String password;

    @NotBlank
    @Size(min = 8, max = 25)
    private String confirmPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;
}

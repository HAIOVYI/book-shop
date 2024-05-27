package mate.academy.mybookshop.service;

import mate.academy.mybookshop.dto.registration.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.registration.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto request);
}

package mate.academy.mybookshop.service;

import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.UserResponseDto;
import mate.academy.mybookshop.exception.RegistrationException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto request) throws RegistrationException;
}

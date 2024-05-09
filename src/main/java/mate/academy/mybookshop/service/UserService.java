package mate.academy.mybookshop.service;

import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto request);
}

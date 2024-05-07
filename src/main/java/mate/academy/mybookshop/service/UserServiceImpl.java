package mate.academy.mybookshop.service;

import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.annotation.PasswordMatchesValidator;
import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.UserResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.exception.RegistrationException;
import mate.academy.mybookshop.mapper.UserMapper;
import mate.academy.mybookshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordMatchesValidator passwordMatchesValidator;

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email already exists");
        }

        if (!passwordMatchesValidator.isValid(request, null)) {
            throw new RegistrationException("Passwords do not match");
        }

        UserEntity user = userMapper.userRegistrationRequestDtoToUserEntity(request);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(savedUser);
    }
}

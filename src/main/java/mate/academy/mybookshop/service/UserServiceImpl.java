package mate.academy.mybookshop.service;

import lombok.RequiredArgsConstructor;
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

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email already exists");
        }

        UserEntity user = userMapper.toEntity(request);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }
}

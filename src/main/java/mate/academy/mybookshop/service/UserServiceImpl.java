package mate.academy.mybookshop.service;

import jakarta.annotation.PostConstruct;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.UserResponseDto;
import mate.academy.mybookshop.entity.RoleEntity;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.exception.RegistrationException;
import mate.academy.mybookshop.mapper.UserMapper;
import mate.academy.mybookshop.repository.RoleRepository;
import mate.academy.mybookshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private long userRoleId;

    @PostConstruct
    void init() {
        userRoleId = roleRepository.findByType(RoleEntity.RoleType.USER).getId();
    }

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email already exists");
        }
        UserEntity user = userMapper.toEntity(request);
        user.setRoles(Set.of(roleRepository.getReferenceById(userRoleId)));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }
}

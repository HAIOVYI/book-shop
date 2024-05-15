package mate.academy.mybookshop.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.config.SecurityConfig;
import mate.academy.mybookshop.dto.UserLoginRequestDto;
import mate.academy.mybookshop.dto.UserLoginResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final SecurityConfig securityConfig;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        Optional<UserEntity> user = userRepository.findByEmail(requestDto.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        String userPasswordFromDb = user.get().getPassword();
        String encodedPassword = securityConfig.passwordEncoder().encode(requestDto.getPassword());
        if (!userPasswordFromDb.equals(encodedPassword)) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new UserLoginResponseDto(jwtUtil.generateToken(requestDto.getEmail()));
    }
}

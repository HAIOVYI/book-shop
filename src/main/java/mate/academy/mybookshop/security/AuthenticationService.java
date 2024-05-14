package mate.academy.mybookshop.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.UserLoginRequestDto;
import mate.academy.mybookshop.dto.UserLoginResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        Optional<UserEntity> user = userRepository.findByEmail(requestDto.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        String userPasswordFromDb = user.get().getPassword();

        return tokenUtil.generateToken(requestDto.getEmail());
    }


}

package mate.academy.mybookshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.login.UserLoginRequestDto;
import mate.academy.mybookshop.dto.login.UserLoginResponseDto;
import mate.academy.mybookshop.dto.registration.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.registration.UserResponseDto;
import mate.academy.mybookshop.exception.RegistrationException;
import mate.academy.mybookshop.security.AuthenticationService;
import mate.academy.mybookshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}

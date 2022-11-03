package hacathon.hacathon.domain.user.service;

import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.domain.user.validate.UserValidator;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserUpdatePasswordRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public void join(UserJoinRequestDto requestDto) {
        if(userRepository.findByName(requestDto.getName()).isPresent()) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_NAME);
        }

        User user = requestDto.toEntity();
        user.addAuthorityUser();
        user.encodedPassword(passwordEncoder);
        userRepository.save(user);
    }

    public TokenResponseDto login(UserLoginRequestDto requestDto) {
        if(userValidator.isAdminUser(requestDto)) {
            return userValidator.loginAdmin(requestDto);
        }
        User user = userRepository.findByName(requestDto.getName())
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_SIGNUP_NAME));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        return userValidator.responseDto(false, requestDto.getName());
    }

    public void updatePassword(UserUpdatePasswordRequestDto requestDto) {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        if(!user.matchPassword(passwordEncoder, requestDto.getBeforePassword())) {
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        user.updatePassword(passwordEncoder, requestDto.getAfterPassword());
    }
}

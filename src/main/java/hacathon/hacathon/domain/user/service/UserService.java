package hacathon.hacathon.domain.user.service;

import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
        if(isAdminUser(requestDto)) {
            return loginAdmin(requestDto);
        }
        User user = userRepository.findByName(requestDto.getName())
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_SIGNUP_NAME));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        return responseDto(false, requestDto.getName());
    }

    private TokenResponseDto loginAdmin(UserLoginRequestDto requestDto) {
        return responseDto(true, requestDto.getName());
    }

    private boolean isAdminUser(UserLoginRequestDto requestDto) {
        return requestDto.getName().equals("admin") && requestDto.getPassword().equals("1234");
    }

    private TokenResponseDto responseDto(boolean isAdmin, String name) {
        final String accessToken = jwtProvider.createAccessToken(name);
        return TokenResponseDto.builder()
                .isAdmin(isAdmin)
                .accessToken(accessToken)
                .build();
    }
}

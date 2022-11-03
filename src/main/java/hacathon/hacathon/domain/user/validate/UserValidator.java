package hacathon.hacathon.domain.user.validate;

import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.domain.user.util.AdminUtil;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.JwtProvider;
import hacathon.hacathon.global.security.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserValidator {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public TokenResponseDto loginAdmin(UserLoginRequestDto requestDto) {
        return responseDto(true, requestDto.getName());
    }

    public boolean isAdminUser(UserLoginRequestDto requestDto) {
        return requestDto.getName().equals(AdminUtil.ADMIN_NAME) && requestDto.getPassword().equals(AdminUtil.ADMIN_PASSWORD);
    }

    public TokenResponseDto responseDto(boolean isAdmin, String name) {
        final String accessToken = jwtProvider.createAccessToken(name);
        return TokenResponseDto.builder()
                .isAdmin(isAdmin)
                .accessToken(accessToken)
                .build();
    }

    public User validateUser() {
        return userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));
    }
}

package hacathon.hacathon.domain.user.validate;

import hacathon.hacathon.domain.user.util.AdminUtil;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserValidator {

    private final JwtProvider jwtProvider;

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
}

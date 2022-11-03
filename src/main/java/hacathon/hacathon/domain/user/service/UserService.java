package hacathon.hacathon.domain.user.service;

import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void join(UserJoinRequestDto requestDto) {
        if(userRepository.findByName(requestDto.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 가입되어있는 이름입니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이름입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
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

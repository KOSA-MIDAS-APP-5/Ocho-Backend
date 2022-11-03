package hacathon.hacathon.domain.user.service;

import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
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

    public void join(UserJoinRequestDto requestDto) {
        if(userRepository.findByName(requestDto.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 가입되어있는 이름입니다.");
        }

        User user = requestDto.toEntity();
        user.encodedPassword(passwordEncoder);
    }

    public void login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByName(requestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이름입니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}

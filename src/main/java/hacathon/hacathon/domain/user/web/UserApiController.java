package hacathon.hacathon.domain.user.web;

import hacathon.hacathon.domain.user.service.UserService;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public void join(@RequestBody @Valid UserJoinRequestDto requestDto) {
        userService.join(requestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return userService.login(requestDto);
    }
}

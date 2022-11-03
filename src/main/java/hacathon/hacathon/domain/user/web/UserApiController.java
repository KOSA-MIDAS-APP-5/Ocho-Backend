package hacathon.hacathon.domain.user.web;

import hacathon.hacathon.domain.user.service.UserService;
import hacathon.hacathon.domain.user.web.dto.request.UserJoinRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.request.UserUpdatePasswordRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/edit/password")
    public void updatePassword(@RequestBody UserUpdatePasswordRequestDto requestDto) {
        userService.updatePassword(requestDto);
    }
}

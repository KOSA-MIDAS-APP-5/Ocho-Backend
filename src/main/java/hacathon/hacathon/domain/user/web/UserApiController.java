package hacathon.hacathon.domain.user.web;

import hacathon.hacathon.domain.user.service.UserService;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody UserLoginRequestDto requestDto) {
        userService.login(requestDto);
    }
}

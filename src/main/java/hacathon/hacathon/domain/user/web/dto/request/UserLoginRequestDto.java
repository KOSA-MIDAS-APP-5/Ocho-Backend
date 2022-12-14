package hacathon.hacathon.domain.user.web.dto.request;

import hacathon.hacathon.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String name;
    private String password;

}

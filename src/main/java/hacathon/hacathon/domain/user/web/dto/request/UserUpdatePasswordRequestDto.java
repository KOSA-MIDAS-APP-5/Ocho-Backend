package hacathon.hacathon.domain.user.web.dto.request;

import lombok.Getter;

@Getter
public class UserUpdatePasswordRequestDto {

    private String beforePassword;
    private String afterPassword;
}

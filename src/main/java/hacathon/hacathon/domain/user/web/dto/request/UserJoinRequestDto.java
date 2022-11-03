package hacathon.hacathon.domain.user.web.dto.request;

import hacathon.hacathon.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 1, max = 20)
    private String name;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*\\W)(?=\\S+$).{5,20}",
            message = "알파벳과 특수기호가 적어도 1개씩 사용해주세요")
    private String password;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }
}

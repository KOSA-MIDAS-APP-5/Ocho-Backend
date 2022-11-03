package hacathon.hacathon.domain.user.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {

    private final boolean isAdmin;
    private final String accessToken;

    @Builder
    public TokenResponseDto(boolean isAdmin, String accessToken) {
        this.isAdmin = isAdmin;
        this.accessToken = accessToken;
    }
}

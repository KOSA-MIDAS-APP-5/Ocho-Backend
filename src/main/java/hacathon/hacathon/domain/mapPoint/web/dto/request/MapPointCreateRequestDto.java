package hacathon.hacathon.domain.mapPoint.web.dto.request;

import hacathon.hacathon.domain.mapPoint.domain.MapPoint;
import lombok.Getter;

@Getter
public class MapPointCreateRequestDto {
    private String latitude;
    private String longitude;

    public MapPoint toEntity() {
        return MapPoint.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}

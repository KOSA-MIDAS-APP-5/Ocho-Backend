package hacathon.hacathon.domain.mapPoint.web.dto.request;

import hacathon.hacathon.domain.mapPoint.domain.MapPoint;
import lombok.Getter;

@Getter
public class MapPointCreateRequestDto {
    private double latitude;
    private double hardness;

    public MapPoint toEntity() {
        return MapPoint.builder()
                .latitude(latitude)
                .hardness(hardness)
                .build();
    }
}

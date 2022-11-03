package hacathon.hacathon.domain.mapPoint.web.dto.response;

import hacathon.hacathon.domain.mapPoint.domain.MapPoint;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MapPointResponseDto {

    private final String name;
    private final double latitude;
    private final double longitude;

    @Builder
    public MapPointResponseDto(MapPoint mapPoint) {
        this.name = mapPoint.getUser().getName();
        this.latitude = mapPoint.getLatitude();
        this.longitude = mapPoint.getLongitude();
    }
}

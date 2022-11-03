package hacathon.hacathon.domain.mapPoint.web;

import hacathon.hacathon.domain.mapPoint.service.MapPointService;
import hacathon.hacathon.domain.mapPoint.web.dto.request.MapPointCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map-point")
public class MapPointApiController {

    private final MapPointService pointService;

    @PostMapping("/new")
    public void createMapPoint(@RequestBody MapPointCreateRequestDto requestDto) {
        pointService.createMapPoint(requestDto);
    }
}

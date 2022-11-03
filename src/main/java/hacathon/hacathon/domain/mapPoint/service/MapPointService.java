package hacathon.hacathon.domain.mapPoint.service;

import hacathon.hacathon.domain.mapPoint.domain.MapPoint;
import hacathon.hacathon.domain.mapPoint.domain.MapPointRepository;
import hacathon.hacathon.domain.mapPoint.exception.MapPointException;
import hacathon.hacathon.domain.mapPoint.exception.MapPointExceptionType;
import hacathon.hacathon.domain.mapPoint.web.dto.request.MapPointCreateRequestDto;
import hacathon.hacathon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MapPointService {

    private final MapPointRepository mapPointRepository;

    public void createMapPoint(MapPointCreateRequestDto requestDto, User user) {
        if(mapPointRepository.existsByUser(user)) {
            throw new MapPointException(MapPointExceptionType.ALREADY_CREATE_MAP_POINT);
        }

        MapPoint point = requestDto.toEntity();
        point.confirmUser(user);

        mapPointRepository.save(point);
    }
}

package hacathon.hacathon.domain.attendance.web;

import hacathon.hacathon.domain.attendance.service.AttendanceService;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceResponseDto;
import hacathon.hacathon.domain.mapPoint.web.dto.request.MapPointCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    @PostMapping("/new")
    public void createAttendance(@RequestBody MapPointCreateRequestDto requestDto) {
        attendanceService.createAttendance(requestDto);
    }

    @GetMapping("")
    public AttendanceResponseDto getUserAttendance() {
        return attendanceService.getUserAttendance();
    }

    @GetMapping("/duty")
    public List<AttendanceAllResponseDto> getDutyUserAttendances() {
        return attendanceService.getDutyUserAttendances();
    }

    @GetMapping("/not/duty")
    public List<AttendanceAllResponseDto> getNotDutyUserAttendances() {
        return attendanceService.getNotDutyUserAttendances();
    }

    @PutMapping("/edit")
    public AttendanceResponseDto updateAttendanceStatus() {
        return attendanceService.updateAttendanceStatus();
    }
}

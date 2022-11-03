package hacathon.hacathon.domain.attendance.web;

import hacathon.hacathon.domain.attendance.service.AttendanceService;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    @PostMapping("/new")
    public void createAttendance() {
        attendanceService.createAttendance();
    }

    @GetMapping("/test")
    public AttendanceResponseDto getAttendance() {
        return attendanceService.getAttendance();
    }
}

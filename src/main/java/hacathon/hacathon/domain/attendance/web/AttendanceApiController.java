package hacathon.hacathon.domain.attendance.web;

import hacathon.hacathon.domain.attendance.service.AttendanceService;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    @PostMapping("/new")
    public void createAttendance() {
        attendanceService.createAttendance();
    }

    @GetMapping("")
    public AttendanceResponseDto getAttendance() {
        return attendanceService.getAttendance();
    }

    @GetMapping("/all")
    public List<AttendanceAllResponseDto> getAttendanceAll() {
        return attendanceService.getAttendanceAll();
    }
}

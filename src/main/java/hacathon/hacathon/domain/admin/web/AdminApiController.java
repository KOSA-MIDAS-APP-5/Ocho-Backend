package hacathon.hacathon.domain.admin.web;

import hacathon.hacathon.domain.admin.service.AdminService;
import hacathon.hacathon.domain.admin.web.dto.request.AdminCreateEssentialGoWorkTimeRequestDto;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminApiController {

    private final AdminService adminService;

    @PutMapping("/edit/{id}")
    public void updateUser(@PathVariable("id") Long id,
                           @RequestBody AdminUpdateUserRequestDto requestDto) {
        adminService.updateUser(id, requestDto);
    }

    @GetMapping("/night-shift")
    public List<AttendanceAllResponseDto> getAttendancesByWorkTime() {
        return adminService.getAttendancesByWorkTime();
    }

    @PutMapping("/go-work-time")
    public void createEssentialGoWorkTime(@RequestBody AdminCreateEssentialGoWorkTimeRequestDto requestDto) {
        adminService.createEssentialGoWorkTime(requestDto);
    }

    @GetMapping("/late-user")
    public List<AttendanceAllResponseDto> getLateUserByEssentialGoWorkTime() {
        return adminService.getLateUserByEssentialGoWorkTime();
    }
}

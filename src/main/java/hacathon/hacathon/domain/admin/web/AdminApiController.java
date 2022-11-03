package hacathon.hacathon.domain.admin.web;

import hacathon.hacathon.domain.admin.service.AdminService;
import hacathon.hacathon.domain.admin.web.dto.request.AdminSettingDutyTimeRequestDto;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.admin.web.dto.response.AdminUserDetailResponseDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.mapPoint.web.dto.response.MapPointResponseDto;
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

    @GetMapping("/{id}")
    public AdminUserDetailResponseDto getUser(@PathVariable("id") Long id) {
        return adminService.getUser(id);
    }

    @PutMapping("/edit/{id}")
    public void updateUser(@PathVariable("id") Long id,
                           @RequestBody AdminUpdateUserRequestDto requestDto) {
        adminService.updateUserName(id, requestDto);
    }

    @GetMapping("/overtime-users")
    public List<AttendanceAllResponseDto> getOvertimeUsers() {
        return adminService.getOvertimeUsers();
    }

    @PutMapping("/setting/duty-time")
    public void settingDutyTime(@RequestBody AdminSettingDutyTimeRequestDto requestDto) {
        adminService.settingDutyTime(requestDto);
    }

    @GetMapping("/late-user")
    public List<AttendanceAllResponseDto> getLateUsers() {
        return adminService.getLateUsers();
    }

    @GetMapping("/map-point")
    public List<MapPointResponseDto> getMapPointAll() {
        return adminService.getMapPointAll();
    }
}

package hacathon.hacathon.domain.admin.web;

import hacathon.hacathon.domain.admin.service.AdminService;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final AdminService adminService;

    @PutMapping("/edit/{id}")
    public void updateUser(@PathVariable("id") Long id,
                           @RequestBody AdminUpdateUserRequestDto requestDto) {
        adminService.updateUser(id, requestDto);
    }
}

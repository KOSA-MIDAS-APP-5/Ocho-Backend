package hacathon.hacathon.domain.admin.service;

import hacathon.hacathon.domain.admin.util.AdminUtil;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.domain.user.web.dto.request.UserLoginRequestDto;
import hacathon.hacathon.domain.user.web.dto.response.TokenResponseDto;
import hacathon.hacathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final JwtProvider jwtProvider;

    public TokenResponseDto loginAdmin(UserLoginRequestDto requestDto) {
        if(!requestDto.getName().equals(AdminUtil.ADMIN_NAME)) {
            throw new UserException(UserExceptionType.NOT_SIGNUP_NAME);
        }

        if(!requestDto.getPassword().equals(AdminUtil.ADMIN_PASSWORD)) {
            throw new UserException(UserExceptionType.WRONG_PASSWORD);
        }

        final String accessToken = jwtProvider.createAccessToken(requestDto.getName());
        return TokenResponseDto.builder()
                .isAdmin(true)
                .accessToken(accessToken)
                .build();
    }

    public void updateUser(Long id, AdminUpdateUserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));

        user.updateUser(requestDto.getName());
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getAttendancesByWorkTime() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getWorkTime().isBefore(attendance.getTodayTotalWorkTime()))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }
}

package portal.vanguardia.service;


import portal.vanguardia.dto.JwtResponseDto;
import portal.vanguardia.dto.LoginDto;
import portal.vanguardia.dto.RegisterDto;
import portal.vanguardia.dto.UserDto;
import org.springframework.http.HttpHeaders;
import portal.vanguardia.entity.User;

import java.util.List;

public interface UserService {
    UserDto register(RegisterDto registerDto);
    JwtResponseDto login(LoginDto loginDto);
    UserDto getLoguedUser(HttpHeaders headers);
    List<UserDto> findUsersByRole(String role);

}

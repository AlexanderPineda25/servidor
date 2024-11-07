package portal.vanguardia.service;


import portal.vanguardia.dto.JwtResponseDto;
import portal.vanguardia.dto.LoginDto;
import portal.vanguardia.dto.RegisterDto;
import portal.vanguardia.dto.UserDto;
import org.springframework.http.HttpHeaders;

public interface UserService {

    UserDto register(RegisterDto registerDto);
    JwtResponseDto login(LoginDto loginDto);
    UserDto getLoguedUser(HttpHeaders headers);
}

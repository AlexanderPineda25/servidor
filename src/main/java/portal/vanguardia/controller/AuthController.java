package portal.vanguardia.controller;

import portal.vanguardia.dto.JwtResponseDto;
import portal.vanguardia.dto.LoginDto;
import portal.vanguardia.dto.RegisterDto;
import portal.vanguardia.dto.UserDto;
import portal.vanguardia.security.JwtGenerator;
import portal.vanguardia.service.RolService;
import portal.vanguardia.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final RolService rolService;
    private final JwtGenerator jwtGenerator;

    public AuthController(UserService userService, RolService rolService, JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.rolService = rolService;
        this.jwtGenerator = jwtGenerator;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto) {
        JwtResponseDto jwtResponse = userService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
        UserDto userDto = userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToke(Authentication authentication){

        String token = jwtGenerator.refreshToken(authentication);

        JwtResponseDto jwtRefresh = new JwtResponseDto(token);
        return new ResponseEntity<JwtResponseDto>(jwtRefresh, HttpStatus.OK);
    }

    @GetMapping("/logued")
    public ResponseEntity<UserDto> getLoguedUser(@RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(userService.getLoguedUser(headers), HttpStatus.OK);
    }
}

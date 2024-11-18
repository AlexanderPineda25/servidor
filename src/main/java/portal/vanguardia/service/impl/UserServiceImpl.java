package portal.vanguardia.service.impl;

import jakarta.annotation.PostConstruct;
import portal.vanguardia.dto.JwtResponseDto;
import portal.vanguardia.dto.LoginDto;
import portal.vanguardia.dto.RegisterDto;
import portal.vanguardia.dto.UserDto;
import portal.vanguardia.entity.User;
import portal.vanguardia.exceptions.ConflictException;
import portal.vanguardia.exceptions.JwtAuthenticationException;
import portal.vanguardia.exceptions.NotFoundException;
import portal.vanguardia.entity.Rol;
import portal.vanguardia.repository.UserRepository;
import portal.vanguardia.security.JwtGenerator;
import portal.vanguardia.service.RolService;
import portal.vanguardia.service.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolService rolService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RolService rolService,
            AuthenticationManager authenticationManager,
            JwtGenerator jwtGenerator,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initDefaultUsersAndRoles() {
        rolService.createAdminRoleIfNotExist();
        rolService.createUserRoleIfNotExist();
        rolService.createStudentRoleIfNotExist();
        rolService.createTeacherRoleIfNotExist();

        createDefaultUser("admin", "admin", "admin@example.com", "ADMIN");
        createDefaultUser("user", "user", "user@example.com", "USER");
        createDefaultUser("student", "student", "student@example.com", "STUDENT");
        createDefaultUser("teacher", "teacher", "teacher@example.com", "TEACHER");
    }

    private void createDefaultUser(String username, String password, String email, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);

            Rol role = rolService.findByname(roleName)
                    .orElseThrow(() -> new NotFoundException("Rol " + roleName + " no encontrado"));
            user.setRoles(Collections.singleton(role));

            userRepository.save(user);
        }
    }

    @Override
    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ConflictException("El usuario ya existe!");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Rol userRole = rolService.findByname("USER")
                .orElseThrow(() -> new NotFoundException("Rol USER no encontrado!"));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    @Override
    public JwtResponseDto login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new JwtResponseDto(token);
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Credenciales inválidas");
        }
    }

    @Override
    public UserDto getLoguedUser(HttpHeaders headers) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    @Override
    public List<UserDto> findUsersByRole(String role) {
        List<User> users = userRepository.findUsersByRole(role);
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRoles()
        );
    }

}
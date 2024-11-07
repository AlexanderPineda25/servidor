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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolService rolService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminUser() {
        // Asegúrate de que el rol ADMIN exista antes de crear el usuario
        rolService.createAdminRoleIfNotExist();

        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));  // Usa una contraseña segura
            adminUser.setEmail("admin@example.com");

            Rol adminRole = rolService.findByname("ADMIN").orElseThrow(() -> new NotFoundException("Rol ADMIN no encontrado"));
            Set<Rol> roles = new HashSet<>();
            roles.add(adminRole);
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
        }
    }

    @PostConstruct
    public void initUserUser() {
        // Asegúrate de que el rol ADMIN exista antes de crear el usuario
        rolService.createUserRoleIfNotExist();

        if (!userRepository.existsByUsername("user")) {
            User userUser = new User();
            userUser.setUsername("user");
            userUser.setPassword(passwordEncoder.encode("user"));  // Usa una contraseña segura
            userUser.setEmail("user@example.com");

            Rol userRole = rolService.findByname("USER").orElseThrow(() -> new NotFoundException("Rol USER no encontrado"));
            Set<Rol> roles = new HashSet<>();
            roles.add(userRole);
            userUser.setRoles(roles);

            userRepository.save(userUser);
        }
    }

    @Override
    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ConflictException("El usuario ya existe!");
        }

        // Crear el usuario y establecer los detalles
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        // Asignar el rol USER automáticamente
        Rol userRole = rolService.findByname("USER")
                .orElseThrow(() -> new NotFoundException("Rol USER no encontrado!"));
        user.setRoles(Collections.singleton(userRole));

        // Guardar el usuario en el repositorio
        userRepository.save(user);

        // Mapear User a UserDto para la respuesta
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

}
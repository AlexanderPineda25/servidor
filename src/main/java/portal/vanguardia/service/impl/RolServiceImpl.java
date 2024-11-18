package portal.vanguardia.service.impl;

import portal.vanguardia.entity.Rol;
import portal.vanguardia.repository.RolRepository;
import portal.vanguardia.service.RolService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Optional<Rol> findByname(String name) {
        return rolRepository.findByName(name);
    }

    @Override
    public void createAdminRoleIfNotExist() {
        if (rolRepository.findByName("ADMIN").isEmpty()) {
            Rol adminRole = new Rol();
            adminRole.setName("ADMIN");
            rolRepository.save(adminRole);
        }
    }

    @Override
    public void createUserRoleIfNotExist() {
        if (rolRepository.findByName("USER").isEmpty()) {
            Rol userRole = new Rol();
            userRole.setName("USER");
            rolRepository.save(userRole);
        }
    }
    @Override
    public void createStudentRoleIfNotExist() {
        if (rolRepository.findByName("STUDENT").isEmpty()) {
            Rol userRole = new Rol();
            userRole.setName("STUDENT");
            rolRepository.save(userRole);
        }
    }

    @Override
    public void createTeacherRoleIfNotExist() {
        if (rolRepository.findByName("TEACHER").isEmpty()) {
            Rol userRole = new Rol();
            userRole.setName("TEACHER");
            rolRepository.save(userRole);
        }
    }
}

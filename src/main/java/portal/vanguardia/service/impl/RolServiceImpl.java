package portal.vanguardia.service.impl;

import portal.vanguardia.entity.Rol;
import portal.vanguardia.repository.RoleRepository;
import portal.vanguardia.service.RolService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RoleRepository roleRepository;

    public RolServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Rol> findByname(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void createAdminRoleIfNotExist() {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Rol adminRole = new Rol();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
    }

    @Override
    public void createUserRoleIfNotExist() {
        if (roleRepository.findByName("USER").isEmpty()) {
            Rol userRole = new Rol();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }
}
package portal.vanguardia.service;

import portal.vanguardia.entity.Rol;

import java.util.Optional;

public interface RolService {
    Optional<Rol> findByname(String name);
    void createAdminRoleIfNotExist();
    void createUserRoleIfNotExist();
    void createStudentRoleIfNotExist();
    void createTeacherRoleIfNotExist();
}

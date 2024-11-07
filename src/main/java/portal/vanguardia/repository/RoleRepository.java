package portal.vanguardia.repository;

import portal.vanguardia.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByName(String name);
}

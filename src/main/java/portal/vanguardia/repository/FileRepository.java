package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}

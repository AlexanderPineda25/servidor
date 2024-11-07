package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}

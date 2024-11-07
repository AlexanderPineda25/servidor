package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {
}

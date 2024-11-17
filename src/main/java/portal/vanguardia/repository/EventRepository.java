package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDate(LocalDate date);
    List<Event> findByCategory(String category);
}

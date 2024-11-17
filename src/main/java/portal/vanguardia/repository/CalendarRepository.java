package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}

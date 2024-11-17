package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Agenda;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByTeacherId(Long teacherId);
    List<Agenda> findByMeetingDate(LocalDate date);
}

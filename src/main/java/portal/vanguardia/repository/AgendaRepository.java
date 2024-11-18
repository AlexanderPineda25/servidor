package portal.vanguardia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import portal.vanguardia.entity.Agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByTeacherId(Long teacherId);
    List<Agenda> findByStudentId(Long studentId);
    List<Agenda> findByMeetingDate(LocalDate meetingDate);
    @Query("SELECT a FROM Agenda a WHERE a.teacher.id = :teacherId AND a.meetingDate = :meetingDate AND " +
            "((:startTime BETWEEN a.startTime AND a.endTime) OR (:endTime BETWEEN a.startTime AND a.endTime) OR " +
            "(a.startTime BETWEEN :startTime AND :endTime))")
    List<Agenda> findConflictingAgendas(
            @Param("teacherId") Long teacherId,
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}

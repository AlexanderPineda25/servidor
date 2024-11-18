package portal.vanguardia.service;

import portal.vanguardia.dto.AgendaResponseDto;
import portal.vanguardia.entity.Agenda;

import java.time.LocalDate;
import java.util.List;

public interface AgendaService {
    List<Agenda> getAllAgendas();
    List<Agenda> getAgendasByTeacherId(Long teacherId);
    List<Agenda> getAgendasByStudentId(Long studentId);
    List<Agenda> getAgendasByDate(LocalDate meetingDate);
    Agenda createAgenda(Agenda agenda);
    void deleteAgenda(Long agendaId);
    void changeAgendaStatus(Long agendaId, Agenda.Status status);
    AgendaResponseDto mapToAgendaResponseDto(Agenda agenda);
}


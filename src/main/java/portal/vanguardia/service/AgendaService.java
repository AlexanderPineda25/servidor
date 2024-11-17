package portal.vanguardia.service;

import portal.vanguardia.entity.Agenda;

import java.time.LocalDate;
import java.util.List;

public interface AgendaService {
    List<Agenda> getAgendasByTeacherId(Long teacherId);
    List<Agenda> getAgendasByDate(LocalDate date);
    Agenda createAgenda(Agenda agenda);
    void deleteAgenda(Long agendaId);
}


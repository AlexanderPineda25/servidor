package portal.vanguardia.service.impl;

import org.springframework.stereotype.Service;
import portal.vanguardia.entity.Agenda;
import portal.vanguardia.repository.AgendaRepository;
import portal.vanguardia.service.AgendaService;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<Agenda> getAgendasByTeacherId(Long teacherId) {
        return agendaRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Agenda> getAgendasByDate(LocalDate date) {
        return agendaRepository.findByMeetingDate(date);
    }

    @Override
    public Agenda createAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public void deleteAgenda(Long agendaId) {
        agendaRepository.deleteById(agendaId);
    }
}

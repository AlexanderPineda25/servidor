package portal.vanguardia.service.impl;
import org.springframework.stereotype.Service;
import portal.vanguardia.dto.AgendaResponseDto;
import portal.vanguardia.dto.UserResponseDto;
import portal.vanguardia.entity.Agenda;
import portal.vanguardia.entity.User;
import portal.vanguardia.repository.AgendaRepository;
import portal.vanguardia.repository.UserRepository;
import portal.vanguardia.service.AgendaService;
import java.time.LocalDate;
import java.util.List;
@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository, UserRepository userRepository) {
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    @Override
    public List<Agenda> getAgendasByTeacherId(Long teacherId) {
        return agendaRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Agenda> getAgendasByStudentId(Long studentId) {
        return agendaRepository.findByStudentId(studentId);
    }

    @Override
    public List<Agenda> getAgendasByDate(LocalDate meetingDate) {
        return agendaRepository.findByMeetingDate(meetingDate);
    }

    @Override
    public Agenda createAgenda(Agenda agenda) {
        User teacher = userRepository.findById(agenda.getTeacher().getId())
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + agenda.getTeacher().getId()));
        User student = userRepository.findById(agenda.getStudent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + agenda.getStudent().getId()));
        agenda.setTeacher(teacher);
        agenda.setStudent(student);
        if (agenda.getStatus() == null) {
            agenda.setStatus(Agenda.Status.PENDING);
        }
        validateAgenda(agenda);
        return agendaRepository.save(agenda);
    }


    @Override
    public void deleteAgenda(Long agendaId) {
        if (!agendaRepository.existsById(agendaId)) {
            throw new IllegalArgumentException("La agenda con ID " + agendaId + " no existe.");
        }
        agendaRepository.deleteById(agendaId);
    }

    @Override
    public void changeAgendaStatus(Long agendaId, Agenda.Status status) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda no encontrada con ID: " + agendaId));
        agenda.setStatus(status);
        agendaRepository.save(agenda);
    }

    private void validateAgenda(Agenda agenda) {
        if (agenda.getStartTime().isAfter(agenda.getEndTime())) {
            throw new IllegalArgumentException("La hora de inicio no puede ser posterior a la hora de finalización.");
        }

        List<Agenda> conflictingAgendas = agendaRepository.findConflictingAgendas(
                agenda.getTeacher().getId(),
                agenda.getMeetingDate(),
                agenda.getStartTime(),
                agenda.getEndTime()
        );

        if (!conflictingAgendas.isEmpty()) {
            throw new IllegalArgumentException("El profesor ya tiene reuniones programadas en este horario.");
        }
    }
    @Override
    public AgendaResponseDto mapToAgendaResponseDto(Agenda agenda) {
        AgendaResponseDto dto = new AgendaResponseDto();
        dto.setId(agenda.getId());
        dto.setParentName(agenda.getParentName());
        dto.setContactInfo(agenda.getContactInfo());
        dto.setMeetingDate(agenda.getMeetingDate().toString());
        dto.setStartTime(agenda.getStartTime().toString());
        dto.setEndTime(agenda.getEndTime().toString());
        dto.setStatus(agenda.getStatus().toString());
        UserResponseDto teacherDto = new UserResponseDto();
        teacherDto.setUsername(agenda.getTeacher().getUsername());
        teacherDto.setEmail(agenda.getTeacher().getEmail());
        dto.setTeacher(teacherDto);
        UserResponseDto studentDto = new UserResponseDto();
        studentDto.setUsername(agenda.getStudent().getUsername());
        studentDto.setEmail(agenda.getStudent().getEmail());
        dto.setStudent(studentDto);
        return dto;
    }
}
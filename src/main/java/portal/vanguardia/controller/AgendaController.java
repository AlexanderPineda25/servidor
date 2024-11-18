package portal.vanguardia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.vanguardia.dto.AgendaRequestDto;
import portal.vanguardia.dto.AgendaResponseDto;
import portal.vanguardia.entity.Agenda;
import portal.vanguardia.entity.User;
import portal.vanguardia.service.AgendaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AgendaResponseDto>> getAgendasByTeacher(@PathVariable Long teacherId, @RequestParam(required = false) String role) {
        List<Agenda> agendas;

        if ("ADMIN".equalsIgnoreCase(role)) {
            agendas = agendaService.getAllAgendas();
        } else {
            agendas = agendaService.getAgendasByTeacherId(teacherId);
        }
        List<AgendaResponseDto> responseDtos = agendas.stream()
                .map(agendaService::mapToAgendaResponseDto)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AgendaResponseDto>> getAgendasByStudent(@PathVariable Long studentId) {
        List<Agenda> agendas = agendaService.getAgendasByStudentId(studentId);
        List<AgendaResponseDto> responseDtos = agendas.stream()
                .map(agendaService::mapToAgendaResponseDto)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/date")
    public ResponseEntity<List<AgendaResponseDto>> getAgendasByDate(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            return ResponseEntity.badRequest().body(null); // Responde con un código 400 si falta el parámetro
        }

        List<Agenda> agendas = agendaService.getAgendasByDate(date);
        List<AgendaResponseDto> responseDtos = agendas.stream()
                .map(agendaService::mapToAgendaResponseDto)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<?> createAgenda(@RequestBody AgendaRequestDto requestDto) {
        if (requestDto.getTeacherId() == null || requestDto.getStudentId() == null) {
            return ResponseEntity.badRequest().body("El ID del profesor y el estudiante son obligatorios.");
        }

        try {
            Agenda agenda = new Agenda();
            agenda.setMeetingDate(LocalDate.parse(requestDto.getMeetingDate()));
            agenda.setStartTime(LocalTime.parse(requestDto.getStartTime()));
            agenda.setEndTime(LocalTime.parse(requestDto.getEndTime()));
            agenda.setParentName(requestDto.getParentName());
            agenda.setContactInfo(requestDto.getContactInfo());

            User teacher = new User();
            teacher.setId(requestDto.getTeacherId());
            agenda.setTeacher(teacher);

            User student = new User();
            student.setId(requestDto.getStudentId());
            agenda.setStudent(student);

            if (agenda.getStatus() == null) {
                agenda.setStatus(Agenda.Status.PENDING);
            }

            Agenda createdAgenda = agendaService.createAgenda(agenda);

            return ResponseEntity.ok(agendaService.mapToAgendaResponseDto(createdAgenda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la agenda.");
        }
    }


    @DeleteMapping("/{agendaId}")
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long agendaId) {
        try {
            agendaService.deleteAgenda(agendaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{agendaId}/status")
    public ResponseEntity<Void> changeAgendaStatus(@PathVariable Long agendaId, @RequestParam Agenda.Status status) {
        try {
            agendaService.changeAgendaStatus(agendaId, status);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


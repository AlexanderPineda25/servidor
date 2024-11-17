package portal.vanguardia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.vanguardia.entity.Agenda;
import portal.vanguardia.service.AgendaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Agenda>> getAgendasByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(agendaService.getAgendasByTeacherId(teacherId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Agenda>> getAgendasByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(agendaService.getAgendasByDate(date));
    }

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda) {
        return ResponseEntity.ok(agendaService.createAgenda(agenda));
    }

    @DeleteMapping("/{agendaId}")
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long agendaId) {
        agendaService.deleteAgenda(agendaId);
        return ResponseEntity.noContent().build();
    }
}


package portal.vanguardia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.vanguardia.entity.Calendar;
import portal.vanguardia.service.CalendarService;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ResponseEntity<List<Calendar>> getAllEvents() {
        return ResponseEntity.ok(calendarService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calendar> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Calendar> createEvent(@RequestBody Calendar event) {
        return ResponseEntity.ok(calendarService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calendar> updateEvent(@PathVariable Long id, @RequestBody Calendar event) {
        return ResponseEntity.ok(calendarService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        calendarService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

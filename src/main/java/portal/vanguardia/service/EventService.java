package portal.vanguardia.service;

import portal.vanguardia.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    List<Event> getEventsByDate(LocalDate date);
    List<Event> getEventsByCategory(String category);
    Event getEventById(Long id);
    Event saveEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
}

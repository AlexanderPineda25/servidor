package portal.vanguardia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portal.vanguardia.entity.Event;
import portal.vanguardia.repository.EventRepository;
import portal.vanguardia.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }
    @Override
    public List<Event> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }
    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event updatedEvent = existingEvent.get();
            updatedEvent.setDate(event.getDate());
            updatedEvent.setTitle(event.getTitle());
            updatedEvent.setDescription(event.getDescription());
            updatedEvent.setCategory(event.getCategory());
            return eventRepository.save(updatedEvent);
        } else {
            throw new RuntimeException("Event not found");
        }
    }
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

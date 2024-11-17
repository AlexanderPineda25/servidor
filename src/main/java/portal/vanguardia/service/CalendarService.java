package portal.vanguardia.service;

import portal.vanguardia.entity.Calendar;
import java.util.List;

public interface CalendarService {
    List<Calendar> getAllEvents();
    Calendar getEventById(Long id);
    Calendar createEvent(Calendar event);
    Calendar updateEvent(Long id, Calendar event);
    void deleteEvent(Long id);
}

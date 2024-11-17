package portal.vanguardia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portal.vanguardia.entity.Calendar;
import portal.vanguardia.exceptions.NotFoundException;
import portal.vanguardia.repository.CalendarRepository;
import portal.vanguardia.service.CalendarService;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }
    @Override
    public List<Calendar> getAllEvents() {
        return calendarRepository.findAll();
    }

    @Override
    public Calendar getEventById(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id " + id));
    }

    @Override
    public Calendar createEvent(Calendar event) {
        return calendarRepository.save(event);
    }

    @Override
    public Calendar updateEvent(Long id, Calendar event) {
        Calendar existingEvent = getEventById(id);
        existingEvent.setActivity(event.getActivity());
        existingEvent.setDateRange(event.getDateRange());
        return calendarRepository.save(existingEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        calendarRepository.deleteById(id);
    }
}


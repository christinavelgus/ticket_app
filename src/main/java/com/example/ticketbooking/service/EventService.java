package com.example.ticketbooking.service;

import com.example.ticketbooking.model.Event;
import com.example.ticketbooking.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findEvents(String name, String type, String location) {
        Stream<Event> stream = eventRepository.findAll().stream();

        if (name != null && !name.isEmpty()) {
            stream = stream.filter(event -> event.getName() != null && event.getName().equalsIgnoreCase(name));
        }
        if (type != null && !type.isEmpty()) {
            stream = stream.filter(event -> event.getType() != null && event.getType().equalsIgnoreCase(type));
        }
        if (location != null && !location.isEmpty()) {
            stream = stream.filter(event -> event.getLocation() != null && event.getLocation().equalsIgnoreCase(location));
        }

        return stream.collect(Collectors.toList());
    }
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}

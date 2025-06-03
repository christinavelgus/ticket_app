package com.example.ticketbooking.controller;

import com.example.ticketbooking.model.DTO.EventDTO;
import com.example.ticketbooking.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.example.ticketbooking.service.EventService;



@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Event>> filterEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location) {

        List<Event> filteredEvents = eventService.findEvents(name, type, location);
        return ResponseEntity.ok(filteredEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setType(eventDTO.getType());
        event.setLocation(eventDTO.getLocation());
        Event saved = eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        Optional<Event> existingEvent = eventService.findById(id);
        if (existingEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event eventToUpdate = existingEvent.get();
        eventToUpdate.setName(eventDTO.getName());
        eventToUpdate.setType(eventDTO.getType());
        eventToUpdate.setLocation(eventDTO.getLocation());
        Event updated = eventService.save(eventToUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

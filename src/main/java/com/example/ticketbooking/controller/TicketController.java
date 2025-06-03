package com.example.ticketbooking.controller;

import com.example.ticketbooking.model.DTO.TicketDTO;
import com.example.ticketbooking.model.Ticket;
import com.example.ticketbooking.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getAll() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody TicketDTO ticket) {
        Ticket saved = ticketService.save(new Ticket(ticket.getEventName(), ticket.getQuantity()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        if (!ticketService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        Ticket updated = ticketService.save(ticket);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!ticketService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

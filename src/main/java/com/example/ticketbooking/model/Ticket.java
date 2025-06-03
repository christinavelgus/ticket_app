package com.example.ticketbooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", length = 100)
    private String eventName;

    @Column(nullable = false)
    private int quantity;

    public Ticket(String eventName, int quantity) {
        this.eventName = eventName;
        this.quantity = quantity;
    }
}

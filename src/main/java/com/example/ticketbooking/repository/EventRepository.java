package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByName(String name);

    List<Event> findByVenueId(Long venueId);

    List<Event> findByEventDateAfter(LocalDate date);

    List<Event> findByVenueNameOrderByEventDateDesc(String venueName);
}


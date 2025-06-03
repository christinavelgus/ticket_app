package com.example.ticketbooking;

import com.example.ticketbooking.model.Event;
import com.example.ticketbooking.model.User;
import com.example.ticketbooking.repository.EventRepository;
import com.example.ticketbooking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ticketbooking.repository.TicketRepository;
import com.example.ticketbooking.model.Ticket;
import com.example.ticketbooking.repository.VenueRepository;
import com.example.ticketbooking.model.Venue;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public DataInitializer(EventRepository eventRepository, VenueRepository venueRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.userRepository =userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) {
        // Місця
        var venue_1 = createVenueIfNotExists(new Venue("Palace Ukraine", "Kyiv, Velyka Vasylkivska 103", 3700));
        var venue_2 = createVenueIfNotExists(new Venue("Lviv Opera", "Lviv, Svobody Ave 28", 1000));
        var venue_3 = createVenueIfNotExists(new Venue("Mystetskyi Arsenal", "Kyiv, Lavrska 10-12", 1500));

        // Події
        //eventRepository.save(new Event("Concert", "Music", "Kyiv", venue_1));
        //eventRepository.save(new Event("Theater", "Drama", "Lviv", venue_2));
        //eventRepository.save(new Event("Exhibition", "Art", "Odessa", venue_3));

        // Квитки
        //ticketRepository.save(new Ticket("Concert", 120));
        //ticketRepository.save(new Ticket("Theater", 80));
        //ticketRepository.save(new Ticket("Exhibition", 50));

        //userRepository.save((new User("admin@admin.com", "admin", "ADMIN")));
       // userRepository.save((new User("user@user.com", "user", "USER")));

    }

    private Venue createVenueIfNotExists(Venue venue) {
        return venueRepository.findByName(venue.getName())
                .orElseGet(() -> venueRepository.save(venue));
    }
}

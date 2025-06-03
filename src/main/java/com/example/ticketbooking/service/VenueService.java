package com.example.ticketbooking.service;

import com.example.ticketbooking.model.Venue;
import com.example.ticketbooking.repository.VenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    @Transactional
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    @Transactional
    public Venue updateVenue(Long id, Venue venueDetails) {
        return venueRepository.findById(id)
                .map(venue -> {
                    venue.setName(venueDetails.getName());
                    venue.setAddress(venueDetails.getAddress());
                    venue.setCapacity(venueDetails.getCapacity());
                    return venueRepository.save(venue);
                })
                .orElseThrow(() -> new RuntimeException("Venue not found with id " + id));
    }

    @Transactional
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new RuntimeException("Venue not found with id " + id);
        }
        venueRepository.deleteById(id);
    }
}

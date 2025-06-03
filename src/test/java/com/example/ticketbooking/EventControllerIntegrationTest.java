package com.example.ticketbooking;

import com.example.ticketbooking.controller.EventController;
import com.example.ticketbooking.model.DTO.EventDTO;
import com.example.ticketbooking.model.Event;
import com.example.ticketbooking.security.JwtRequestFilter;
import com.example.ticketbooking.security.JwtUtil;
import com.example.ticketbooking.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    void testCreateEvent() throws Exception {
        EventDTO eventDto = new EventDTO();
        eventDto.setName("Mock Event");
        eventDto.setType("Festival");
        eventDto.setLocation("Lviv");

        Event savedEvent = new Event();
        savedEvent.setId(1L);
        savedEvent.setName(eventDto.getName());
        savedEvent.setType(eventDto.getType());
        savedEvent.setLocation(eventDto.getLocation());

        when(eventService.save(ArgumentMatchers.any(Event.class))).thenReturn(savedEvent);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mock Event"))
                .andExpect(jsonPath("$.location").value("Lviv"));
    }
}

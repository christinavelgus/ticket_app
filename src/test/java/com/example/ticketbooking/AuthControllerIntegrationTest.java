//package com.example.ticketbooking;
//
//import com.example.ticketbooking.model.DTO.RegisterRequest;
//import com.example.ticketbooking.repository.UserRepository;
//import com.example.ticketbooking.model.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//@ActiveProfiles("test")
//public class AuthControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void registerUser_ShouldCreateUserInDatabase() throws Exception {
//        RegisterRequest request = new RegisterRequest();
//        request.setEmail("newuser@example.com");
//        request.setPassword("securePass123");
//
//        String json = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//
//        Optional<User> createdUser = userRepository.findByEmail("newuser@example.com");
//        assertTrue(createdUser.isPresent());
//        assertNotEquals("securePass123", createdUser.get().getPassword());
//    }
//}

package com.example.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.example.ticketbooking.repository")
@EntityScan("com.example.ticketbooking.model")
public class TicketAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketAppApplication.class, args);
    }
}
//@SpringBootApplication
//public class TicketAppApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(TicketAppApplication.class, args);
//    }
//}

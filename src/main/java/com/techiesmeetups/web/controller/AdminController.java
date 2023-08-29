package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.service.ClubService;
import com.techiesmeetups.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private EventService eventService;


    @GetMapping("/api/admin/info")
    public ResponseEntity<?> getAdminInfo() {
        Map<String, Object> info = new HashMap<>();

        try {
            info.put("numberOfMeetups",eventService.getNumberOfEvents());
            info.put("meetups", eventService.getAllEvents());
            info.put("numberOfClubs", clubService.getNumberOfClubs());
            info.put("clubs", clubService.findAllClubs());

            return new ResponseEntity<>(info, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to retrieve admin info.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
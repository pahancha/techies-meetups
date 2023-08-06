package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.models.Event;
import com.techiesmeetups.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/events")
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/api/{clubID}/events")
    public ResponseEntity<String> createEvent(@PathVariable("clubID") Long clubID,
                                              @RequestBody EventDTO eventDTO) {
        try {
            eventService.createEvent(clubID, eventDTO);
            return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to create event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

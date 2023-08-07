package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.models.Event;
import com.techiesmeetups.web.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("api/events")
    public ResponseEntity<List<EventDTO>> eventsList(){
        List<EventDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/api/events/{eventId}")
    public ResponseEntity<EventDTO> viewEvent(@PathVariable("eventId") Long eventId) {
        try {
            EventDTO event = eventService.findByEventId(eventId);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/api/events/{eventId}")
    public ResponseEntity<String> updateEvent(
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody EventDTO updatedEventDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            // Construct a list of validation error messages
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            // Return the validation error messages with BAD_REQUEST status
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!eventId.equals(updatedEventDTO.getId())) {
            return new ResponseEntity<>("Event ID mismatch", HttpStatus.BAD_REQUEST);
        }

        try {
            eventService.updateEvent(eventId, updatedEventDTO);
            return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
    }






}

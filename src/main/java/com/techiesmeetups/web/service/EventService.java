package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.EventDTO;

import java.util.List;

public interface EventService {
    void createEvent(Long clubID, EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    EventDTO findByEventId(Long eventId);

    void updateEvent(Long eventId,EventDTO eventDTO);

    void deleteEvent(long eventId);
     int getNumberOfEvents();
}

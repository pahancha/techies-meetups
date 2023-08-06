package com.techiesmeetups.web.service.impl;

import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.models.Club;
import com.techiesmeetups.web.models.Event;
import com.techiesmeetups.web.repository.ClubRepository;
import com.techiesmeetups.web.repository.EventRepository;
import com.techiesmeetups.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.techiesmeetups.web.mapper.EventMapper.mapToEvent;
import static com.techiesmeetups.web.mapper.EventMapper.mapToEventDTO;

@Service
public class EventServiceImpl implements EventService {
    private ClubRepository clubRepository;
    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubID, EventDTO eventDTO) {
        Club club = clubRepository.findById(clubID).get();
        Event event = mapToEvent(eventDTO);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return  mapToEventDTO(event);
    }
}

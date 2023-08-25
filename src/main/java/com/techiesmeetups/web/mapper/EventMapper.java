package com.techiesmeetups.web.mapper;

import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.models.Event;

public class EventMapper {

    public static Event mapToEvent(EventDTO eventDTO) {
        return Event.builder()
                .id(eventDTO.getId())
                .name(eventDTO.getName())
                .type(eventDTO.getType())
                .startTime(eventDTO.getStartTime())
                .endTime(eventDTO.getEndTime())
                .createdOn(eventDTO.getCreatedOn())
                .updatedOn(eventDTO.getUpdatedOn())
                .photoURL(eventDTO.getPhotoURL())
//                .club(eventDTO.getClub())
                .build();
    }

    public static EventDTO mapToEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .type(event.getType())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .createdOn(event.getCreatedOn())
                .updatedOn(event.getUpdatedOn())
                .photoURL(event.getPhotoURL())
//                .club(event.getClub())
                .clubId(event.getClub().getId())
                .clubName(event.getClub().getTitle())
                .build();
    }
}

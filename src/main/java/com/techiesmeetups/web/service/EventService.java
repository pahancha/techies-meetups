package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.EventDTO;

public interface EventService {
    void createEvent(Long clubID, EventDTO eventDTO);
}

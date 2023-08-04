package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAllClubs();
    Club create(Club club);

    ClubDTO findMeetByID(long meetID);


}

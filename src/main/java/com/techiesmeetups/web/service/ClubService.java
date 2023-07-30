package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.ClubDTO;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAllClubs();

}

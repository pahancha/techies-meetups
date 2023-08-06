package com.techiesmeetups.web.mapper;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.models.Club;

import java.util.stream.Collectors;

import static com.techiesmeetups.web.mapper.EventMapper.mapToEventDTO;

public class ClubMapper {

    public static Club mapToClub(ClubDTO club) {
        Club clubDto = Club.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoURL(club.getPhotoURL())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getCreatedOn())
                .build();

        return clubDto;
    }


    public static ClubDTO mapToClubDto(Club club) {
        ClubDTO clubDTO = ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoURL(club.getPhotoURL())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map((event) -> mapToEventDTO(event)).collect(Collectors.toList()))
                .build();

        return clubDTO;
    }
}

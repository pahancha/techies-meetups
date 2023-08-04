package com.techiesmeetups.web.service.impl;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.models.Club;
import com.techiesmeetups.web.repository.ClubRepository;
import com.techiesmeetups.web.service.ClubService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository  = clubRepository;
    }
    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club create(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public ClubDTO findMeetByID(long clubID) {
        Club club = clubRepository.findById(clubID).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(long clubID) {
        Optional<Club> clubOptional = clubRepository.findById(clubID);
        Club existingClub = clubOptional.get();
        existingClub.setUpdatedOn(LocalDateTime.now());
        // Update other fields as needed
        clubRepository.save(existingClub);

    }

    private Club mapToClub(ClubDTO club) {
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


    private ClubDTO mapToClubDto(Club club) {
        ClubDTO clubDTO = ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoURL(club.getPhotoURL())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .build();

        return clubDTO;
    }
}

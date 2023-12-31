package com.techiesmeetups.web.service.impl;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.models.Club;
import com.techiesmeetups.web.models.UserEntity;
import com.techiesmeetups.web.repository.ClubRepository;
import com.techiesmeetups.web.repository.UserRepository;
import com.techiesmeetups.web.service.ClubService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.techiesmeetups.web.mapper.ClubMapper.mapToClub;
import static com.techiesmeetups.web.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;
    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository  = clubRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();

        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club create(ClubDTO clubDTO) {
        String username = clubDTO.getCreatedBy();
        UserEntity user = userRepository.findFirstByUserName(username);
        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDTO findMeetByID(long clubID) {
        Club club = clubRepository.findById(clubID).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {
        String username = clubDTO.getCreatedBy();
        UserEntity user = userRepository.findByUserName(username);

        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void deleteClub(Long clubID) {
        clubRepository.deleteById(clubID);
    }

    @Override
    public List<ClubDTO> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }
    @Override
    public int getNumberOfClubs(){
        return clubRepository.findAll().size();
    }

}

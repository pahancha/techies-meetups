package com.techiesmeetups.web.service.impl;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.dto.EventDTO;
import com.techiesmeetups.web.dto.UserInfoDTO;
import com.techiesmeetups.web.mapper.ClubMapper;
import com.techiesmeetups.web.models.Club;
import com.techiesmeetups.web.models.UserEntity;
import com.techiesmeetups.web.repository.ClubRepository;
import com.techiesmeetups.web.repository.EventRepository;
import com.techiesmeetups.web.repository.UserRepository;
import com.techiesmeetups.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserRepository userRepository;
    private ClubRepository clubRepository;
    private EventRepository eventRepository;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository, ClubRepository clubRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public UserInfoDTO getUserInfo(Long userid) {
        Optional<UserEntity> user = userRepository.findById(userid);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();

            List<Club> clubs = clubRepository.findByCreatedBy(userEntity);

            List<ClubDTO> clubDTOs = clubs.stream()
                    .map(ClubMapper::mapToClubDto)
                    .collect(Collectors.toList());

            return  UserInfoDTO.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUserName())
                    .clubs(clubDTOs)
                    .build();
        }

        return null;
    }
}

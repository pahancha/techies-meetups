package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.models.Club;
import com.techiesmeetups.web.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ClubDTO>> listClubs() {
        List<ClubDTO> clubs = clubService.findAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createClub(@RequestBody Club club) {
        clubService.create(club);
        return new ResponseEntity<>("Club created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{clubID}")
    public ResponseEntity<ClubDTO> getClub(@PathVariable("clubID") long clubID) {
        ClubDTO club = clubService.findMeetByID(clubID);
        if (club == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(club, HttpStatus.OK);
    }

    @PutMapping("/{clubID}")
    public ResponseEntity<String> updateClub(@PathVariable("clubID") long clubID) {
            clubService.updateClub(clubID);
            return new ResponseEntity<>("Club updated successfully", HttpStatus.OK);
    }




}

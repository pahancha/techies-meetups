package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> createClub(@RequestBody ClubDTO clubDTO) {
        clubService.create(clubDTO);
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
    public ResponseEntity<String> updateClub(@PathVariable("clubID") Long clubID,
                                             @Valid @RequestBody ClubDTO club,
                                             BindingResult result) {
        if (result.hasErrors()) {
            // Construct a list of validation error messages
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            // Return the validation error messages with BAD_REQUEST status
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        if (!clubID.equals(club.getId())) {
            return new ResponseEntity<>("Club ID mismatch", HttpStatus.BAD_REQUEST);
        }

        try {
            clubService.updateClub(club);
            return new ResponseEntity<>("Club updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Club not found", HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/{clubID}/edit")
//    public ResponseEntity<String> updateClub(@PathVariable("clubID") Long clubID, @RequestBody ClubDTO club) {
//        if (!clubID.equals(club.getId())) {
//            return new ResponseEntity<>("Club ID mismatch", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            clubService.updateClub(club);
//            return new ResponseEntity<>("Club updated successfully", HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>("Club not found", HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{clubID}")
    public ResponseEntity<String> deleteClub(@PathVariable("clubID") Long clubID) {
        try {
            clubService.deleteClub(clubID);
            return new ResponseEntity<>("Club deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Club not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<ClubDTO>> searchClub(@RequestParam(value = "query") String query) {
        List<ClubDTO> clubs = clubService.searchClubs(query);
        return ResponseEntity.ok(clubs);
    }





}

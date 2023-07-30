package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.ClubDTO;
import com.techiesmeetups.web.service.ClubService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClubController {
    private ClubService clubService;


    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        List<ClubDTO> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

//    REST api
    @GetMapping("/api/clubs")
    public List<ClubDTO> listClubs() {
        return clubService.findAllClubs();
    }
}

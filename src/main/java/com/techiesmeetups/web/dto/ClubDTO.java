package com.techiesmeetups.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class ClubDTO {
    private Long id;
    @NotEmpty(message = "Club title should not be empty.")
    private String title;
    @NotEmpty(message = "Photo URL should not be empty.")
    private String photoURL;
    @NotEmpty(message = "Content should not be empty.")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDTO> events;
}

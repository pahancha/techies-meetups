package com.techiesmeetups.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ClubDTO {
    private Long id;
    private String title;
    private String photoURL;
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

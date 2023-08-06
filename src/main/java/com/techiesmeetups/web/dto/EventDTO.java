package com.techiesmeetups.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String type;
    private String photoURL;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

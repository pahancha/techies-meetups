package com.techiesmeetups.web.dto;

import com.techiesmeetups.web.models.Event;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class UserInfoDTO {
    private Long id;
    private String username;
    private List<ClubDTO> clubs;
}

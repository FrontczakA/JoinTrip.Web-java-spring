package com.projekt.web.dto;

import com.projekt.web.models.Comment;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TripDto {

    private Long id;
    @NotEmpty(message = "This field cant be empty!")
    private String title;
    @NotEmpty(message = "This field cant be empty!")
    private String photoUrl;
    @NotEmpty(message = "This field cant be empty!")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
    private List<EventDto> events;
    private List<Comment> comments;

}

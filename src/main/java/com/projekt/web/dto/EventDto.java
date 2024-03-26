package com.projekt.web.dto;

import com.projekt.web.models.Trip;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;

    @NotEmpty(message = "This field cant be empty!")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    private LocalDateTime endTime;

    @NotEmpty(message = "This field cant be empty!")
    private String photoUrl;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Trip trip;
    private String description;
}

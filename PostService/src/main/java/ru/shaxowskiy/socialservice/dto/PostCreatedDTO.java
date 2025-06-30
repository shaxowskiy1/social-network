package ru.shaxowskiy.socialservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostCreatedDTO {
    private String username;
    private String content;

    private LocalDateTime createdAt;
}

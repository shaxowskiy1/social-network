package ru.shaxowskiy.socialservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


public class PostCreatedDTO {
    private UUID id;
    private String username;
    private String content;

    private LocalDateTime createdAt;

    public PostCreatedDTO() {
    }

    public PostCreatedDTO(UUID uuid, String username, String content, LocalDateTime createdAt) {
        this.id = uuid;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return id;
    }

    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

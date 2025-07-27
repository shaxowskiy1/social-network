package ru.shaxowskiy.socialservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class PostCreateDTO {

    @NotEmpty(message = "Post content should not be empty")
    @Size(min = 3, max = 30, message = "Post content should be between 3 and 30 characters")
    private String content;

    public @NotEmpty(message = "Post content should not be empty") @Size(min = 3, max = 30, message = "Post content should be between 3 and 30 characters") String getContent() {
        return content;
    }

    public void setContent(@NotEmpty(message = "Post content should not be empty") @Size(min = 3, max = 30, message = "Post content should be between 3 and 30 characters") String content) {
        this.content = content;
    }
}

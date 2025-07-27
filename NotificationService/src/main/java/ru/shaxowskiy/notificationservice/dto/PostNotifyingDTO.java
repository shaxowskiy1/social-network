package ru.shaxowskiy.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostNotifyingDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String content;

}

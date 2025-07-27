package ru.shaxowskiy.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.shaxowskiy.notificationservice.dto.PostNotifyingDTO;

@Slf4j
@Service
public class NotificationService {

    //TODO вынести общую модель
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "post-1")
    public String createNotify(String jsonPostNotifyingDTO){
        PostNotifyingDTO postNotifyingDTO = null;
        try {
            postNotifyingDTO = new ObjectMapper().readValue(jsonPostNotifyingDTO, PostNotifyingDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга JSON: {}", jsonPostNotifyingDTO);
            throw new RuntimeException(e);
        }
        log.info("Notify consumed : {}", postNotifyingDTO);
        return postNotifyingDTO.getContent();
    }
}

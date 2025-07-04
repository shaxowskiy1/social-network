package ru.shaxowskiy.socialservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.models.Post;
import ru.shaxowskiy.socialservice.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private KafkaTemplate<String, Post> kafkaTemplate;

    public PostService(PostRepository postRepository, KafkaTemplate<String, Post> kafkaTemplate) {
        this.postRepository = postRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Post save(PostCreateDTO postCreateDTO, String username){
        Post post = Post.builder()
                .username(username)
                .content(postCreateDTO.getContent())
                .createdAt(LocalDateTime.now())
                .fileId(null)
                .likes(null)
                .comments(null).build();
        Post savedPost = postRepository.save(post);

        CompletableFuture<SendResult<String, Post>> future =
                kafkaTemplate.send("post-created-events-topic", savedPost.getId().toString(), post);


        future.whenComplete((stringPostSendResult, throwable) -> {
            if(throwable != null){
                log.info("Failed to send message: {}", throwable.getMessage());
            }
            log.info("Message is successfull. Topic {}, Partition {}, Offset {}",
                    stringPostSendResult.getRecordMetadata().topic(),
                    stringPostSendResult.getRecordMetadata().partition(),
                    stringPostSendResult.getRecordMetadata().offset());
        });
        return post;
    }
}

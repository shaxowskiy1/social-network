package ru.shaxowskiy.socialservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.models.Post;
import ru.shaxowskiy.socialservice.repository.PostRepository;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
        postRepository.save(post);
        return post;
    }
}

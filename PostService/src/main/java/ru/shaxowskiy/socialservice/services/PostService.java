package ru.shaxowskiy.socialservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.dto.PostCreatedDTO;
import ru.shaxowskiy.socialservice.mapper.PostCreateMapperImpl;
import ru.shaxowskiy.socialservice.mapper.PostCreatedMapperImpl;
import ru.shaxowskiy.socialservice.models.Post;
import ru.shaxowskiy.socialservice.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostCreateMapperImpl postCreateMapperImpl;
    private final PostCreatedMapperImpl postCreatedMapperImpl;


    public PostService(PostRepository postRepository, PostCreateMapperImpl postCreateMapperImpl, PostCreatedMapperImpl postCreatedMapperImpl) {
        this.postRepository = postRepository;
        this.postCreateMapperImpl = postCreateMapperImpl;
        this.postCreatedMapperImpl = postCreatedMapperImpl;
    }

    @Transactional
    public PostCreatedDTO save(PostCreateDTO postCreateDTO, String username){
        Post post = postCreateMapperImpl.postCreatedToPost(postCreateDTO);
        post.setUsername(username);
        post.setCreatedAt(LocalDateTime.now());
        Post savingPost = postRepository.save(post);


        PostCreatedDTO postCreatedDTO = postCreatedMapperImpl.postToPostCreatedDTO(savingPost);
        postCreatedDTO.setUuid(post.getId());
        return postCreatedDTO;
    }

    public PostCreatedDTO getPost(UUID uuid) {
        Post postFromDB = postRepository.findPostById(uuid).orElseThrow(null);
        return postCreatedMapperImpl.postToPostCreatedDTO(postFromDB);
    }

    @Transactional
    public PostCreatedDTO update(PostCreateDTO postCreateDTO, UUID uuid){
        Post foundPost = postRepository.findById(uuid)
                .orElseThrow(null);

        foundPost.setContent(postCreateDTO.getContent());
        postRepository.save(foundPost);
        return postCreatedMapperImpl.postToPostCreatedDTO(foundPost);
    }
}

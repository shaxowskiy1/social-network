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
    private final PostCreateMapperImpl postCreateMapper;
    private final PostCreatedMapperImpl postCreatedMapperImpl;


    public PostService(PostRepository postRepository, PostCreateMapperImpl postCreateMapper, PostCreatedMapperImpl postCreatedMapperImpl) {
        this.postRepository = postRepository;
        this.postCreateMapper = postCreateMapper;
        this.postCreatedMapperImpl = postCreatedMapperImpl;
    }

    @Transactional
    public PostCreatedDTO save(PostCreateDTO postCreateDTO, String username){
        Post post = postCreateMapper.postCreatedToPost(postCreateDTO);
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
}

package ru.shaxowskiy.socialservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shaxowskiy.cloudfilestorage.JwtRequest;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.dto.PostCreatedDTO;
import ru.shaxowskiy.socialservice.models.Post;
import ru.shaxowskiy.socialservice.services.AuthServiceImpl;
import ru.shaxowskiy.socialservice.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    private final AuthServiceImpl authService;
    private final PostService postService;

    public PostController(AuthServiceImpl authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostCreatedDTO> createPost(
            @RequestBody PostCreateDTO postCreateDTO,
            @RequestHeader(name = "Authorization") String jwtToken){
        //TODO прочитать токен из cookie и внести в парам
        JwtRequest.ValidateTokenResponse validateTokenResponse = authService.validateTokenResponse(jwtToken);
        String username = validateTokenResponse.getUsername();
        Post savedPost = postService.save(postCreateDTO, username);
        return ResponseEntity.ok(new PostCreatedDTO(savedPost.getUsername(), savedPost.getContent(), savedPost.getCreatedAt()));
    }
}

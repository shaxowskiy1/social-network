package ru.shaxowskiy.socialservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.dto.PostCreatedDTO;
import ru.shaxowskiy.socialservice.services.AuthServiceImpl;
import ru.shaxowskiy.socialservice.services.PostService;

import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {
    private final AuthServiceImpl authService;
    private final PostService postService;

    public PostController(AuthServiceImpl authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostCreatedDTO> getPost(
            @RequestParam("uuid") UUID uuid){
        PostCreatedDTO postFromDB = postService.getPost(uuid);
        return ResponseEntity.ok(postFromDB);
    }


    @PostMapping
    public ResponseEntity<PostCreatedDTO> createPost(
            @RequestBody PostCreateDTO postCreateDTO,
            @RequestHeader(name = "X-User-Name") String username){
        //TODO прочитать токен из cookie и внести в парам
        //JwtRequest.ValidateTokenResponse validateTokenResponse = authService.validateTokenResponse(jwtToken);
        //String username = validateTokenResponse.getUsername();
        PostCreatedDTO savedPost = postService.save(postCreateDTO, username);
        return ResponseEntity.ok(savedPost);
    }

    @PatchMapping
    public ResponseEntity<PostCreatedDTO> updatePost(
            @RequestParam("uuid") UUID uuid,
            @RequestBody PostCreateDTO postCreateDTO,
            @RequestHeader(name = "X-User-Name") String username){
        PostCreatedDTO savedPost = postService.update(postCreateDTO, uuid);
        return ResponseEntity.ok(savedPost);
    }
}

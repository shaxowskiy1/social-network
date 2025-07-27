package ru.shaxowskiy.socialservice.mapper;

import org.mapstruct.Mapper;
import ru.shaxowskiy.socialservice.dto.PostCreatedDTO;
import ru.shaxowskiy.socialservice.models.Post;

@Mapper(componentModel = "spring")
public interface PostCreatedMapper {
    Post postCreatedToPost(PostCreatedDTO postCreatedDTO);
    PostCreatedDTO postToPostCreatedDTO(Post post);
}

package ru.shaxowskiy.socialservice.mapper;

import org.mapstruct.Mapper;
import ru.shaxowskiy.socialservice.dto.PostCreateDTO;
import ru.shaxowskiy.socialservice.dto.PostCreatedDTO;
import ru.shaxowskiy.socialservice.models.Post;

@Mapper(componentModel = "spring")
public interface PostCreateMapper {

    Post postCreatedToPost(PostCreateDTO postCreateDTO);
    PostCreateDTO postToPostCreateDTO(Post post);
}

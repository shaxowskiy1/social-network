package ru.shaxowskiy.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shaxowskiy.socialservice.models.Post;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

}

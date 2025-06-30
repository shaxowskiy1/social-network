package ru.shaxowskiy.socialservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @EmbeddedId
    private LikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UUID userId;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

class LikeId implements Serializable {
    private UUID postId;
//    private UUID userId;
}

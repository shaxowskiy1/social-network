package ru.shaxowskiy.socialservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription{

    //@EmbeddedId
    //private SubscriptionId id;

    @Id
    @Column(name = "follower_id", nullable = false, insertable = false, updatable = false)
    private UUID followerId;

    @Column(name = "following_id", nullable = false, insertable = false, updatable = false)
    private UUID followingId;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class SubscriptionId implements Serializable {
    private UUID followerId;
    private UUID followingId;
}

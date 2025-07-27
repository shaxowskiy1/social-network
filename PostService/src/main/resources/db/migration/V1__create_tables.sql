CREATE TABLE posts (
                       id UUID PRIMARY KEY,
                       user_id UUID NOT NULL,
                       content TEXT,
                       file_id VARCHAR(255) default null,
                       created_at TIMESTAMP
);

CREATE TABLE comments (
                          id UUID PRIMARY KEY,
                          post_id UUID REFERENCES posts(id),
                          user_id UUID NOT NULL,
                          content TEXT,
                          created_at TIMESTAMP
);

CREATE TABLE likes (
                       post_id UUID REFERENCES posts(id),
                       user_id UUID NOT NULL,
                       PRIMARY KEY (post_id, user_id)
);

CREATE TABLE subscriptions (
                               follower_id UUID NOT NULL,
                               following_id UUID NOT NULL,
                               PRIMARY KEY (follower_id, following_id)
);

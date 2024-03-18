package com.TooMeet.Post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@IdClass(CommentReactionId.class)
public class CommentReaction {

    @Id
    private Long userId;
    private int emoji = -1;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    @Id
    private Comment comment;
    public void setPost(Comment comment) {
        this.comment = comment;
    }

}

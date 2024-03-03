package com.TooMeet.Post.controller;

import com.TooMeet.Post.entity.Comment;
import com.TooMeet.Post.repository.CommentRepository;
import com.TooMeet.Post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts/comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;

    @PostMapping("")
    public Comment newComment(@RequestParam("comment") String comment,
                              @RequestParam("replyId") long replyId,
                              @RequestParam("postId") UUID postId,
                              @RequestHeader("x-user-id") long userId){
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setUserId(userId);
        newComment.setPostId(postId);
        newComment.setReplyId(replyId);

        return commentService.saveComment(newComment);

    }



}

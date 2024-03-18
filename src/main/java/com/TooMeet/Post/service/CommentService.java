package com.TooMeet.Post.service;

import com.TooMeet.Post.entity.Comment;
import com.TooMeet.Post.repository.CommentRepository;
import com.TooMeet.Post.resposn.AuthorDto;
import com.TooMeet.Post.resposn.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Value("${author.service.url}")
    private String authorServiceUrl;
    @Autowired
    CommentRepository commentRepository;

    public Comment saveComment(Comment comment){

        if(comment.getId() == null){
            return commentRepository.save(comment);
        }
        else {
            return commentRepository.save(comment);
        }
    }

    public Comment getCommentByParentId(UUID parentId){
        return commentRepository.findByParentId(parentId);
    }

    public Page<CommentResponse> getComments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").ascending());
        Page<Comment> commentPage = commentRepository.findAll(pageable);
        return commentPage.map(this::convertToResponse);
    }

    public CommentResponse convertToResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setReaction(1);
        commentResponse.setContent(comment.getContent());
        commentResponse.setLevel(comment.getLevel());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());
        commentResponse.setLikeCount(0);
        return commentResponse;
    }

    public Page<CommentResponse> getCommentsByParentId(UUID parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").ascending());
        Page<Comment> commentPage = commentRepository.findByParentId(parentId, pageable);
        return commentPage.map(this::convertToResponse);
    }

    public Page<CommentResponse> getCommentsByParentIdAndLevel(UUID parentId, int level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findByParentIdAndLevel(parentId, level, pageable);

        return comments.map(comment -> {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setContent(comment.getContent());
            commentResponse.setLevel(comment.getLevel());
            commentResponse.setLikeCount(comment.getLikeCount());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setUpdatedAt(comment.getUpdatedAt());
            commentResponse.setReplyCount(getReplyCount(comment));
            return commentResponse;
        });
    }

    private int getReplyCount(Comment comment) {
        int replyCount = 0;
        if(comment.getLevel() < 2){
            List<Comment> repliesComment = commentRepository.findRepliesWithMultipleRepliesByCommentId(comment.getId());
            for(Comment replyComment : repliesComment){
                if (comment.getLevel()==0){
                    List<Comment> repliesComments = commentRepository.findRepliesWithMultipleRepliesByCommentId(replyComment.getId());
                    for(Comment comment1 : repliesComments){
                        replyCount = replyCount + getRC(comment1);
                    }
                }
                replyCount= replyCount + getRC(replyComment);
            }
        }

        return replyCount;
    }

    private int getRC(Comment comment){
        return commentRepository.countByParentId(comment.getId());
    }

}

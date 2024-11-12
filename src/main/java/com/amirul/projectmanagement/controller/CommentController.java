package com.amirul.projectmanagement.controller;

import com.amirul.projectmanagement.model.Comment;
import com.amirul.projectmanagement.model.User;
import com.amirul.projectmanagement.request.CreateCommentRequest;
import com.amirul.projectmanagement.response.MessageResponse;
import com.amirul.projectmanagement.service.CommentService;
import com.amirul.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String token
    ) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        Comment createComment = commentService.createComment(
                req.getIssueId(),
                user.getId(),
                req.getContent()
        );
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String token
    ) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(
            @PathVariable Long issueId
    ) throws Exception {

        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}

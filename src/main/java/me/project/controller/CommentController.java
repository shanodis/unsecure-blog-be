package me.project.controller;

import lombok.AllArgsConstructor;
import me.project.dto.CommentListResponse;
import me.project.dto.CommentRequest;
import me.project.entitiy.Comment;
import me.project.service.CommentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentListResponse> getApprovedComments() {
        return commentService.getApprovedComments();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/unverified")
    public List<Comment> getCommentsToVerify() {
        return commentService.getCommentsToVerify();
    }

    @Secured({"ROLE_USER"})
    @PostMapping("{myUserId}/add")
    public Comment addComment(@PathVariable UUID myUserId, @RequestBody CommentRequest request) {
        return commentService.addComment(myUserId, request.getCommentText());
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{commentId}/approve")
    public Comment approveComment(@PathVariable UUID commentId, @RequestBody CommentRequest request) {
        return commentService.approveComment(commentId, request.getIsCommentApproved());
    }
}

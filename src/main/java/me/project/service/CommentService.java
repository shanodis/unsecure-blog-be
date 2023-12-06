package me.project.service;

import lombok.AllArgsConstructor;
import me.project.dto.CommentListResponse;
import me.project.entitiy.Comment;
import me.project.entitiy.MyUser;
import me.project.repository.CommentRepository;
import me.project.repository.MyUserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MyUserRepository myUserRepository;

    public List<CommentListResponse> getApprovedComments() {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getIsCommentAccepted() != null && comment.getIsCommentAccepted())
                .map(comment -> {
                    CommentListResponse commentListResponse = new CommentListResponse();
                    commentListResponse.setCommentId(comment.getCommentId());
                    commentListResponse.setCommentText(comment.getCommentText());
                    commentListResponse.setCreatedBy(comment.getMyUser().getUsername());
                    return commentListResponse;
                })
                .collect(Collectors.toList());
    }

    public Comment addComment(UUID myUserId, String commentName) {
        MyUser myUser = myUserRepository.findById(myUserId).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        Comment comment = new Comment();
        comment.setCommentText(commentName);
        comment.setMyUser(myUser);
        return commentRepository.save(comment);
    }

    public Comment approveComment(UUID commentId, Boolean isCommentApproved) {
        Comment comment = commentRepository.getById(commentId);
        comment.setIsCommentAccepted(isCommentApproved);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsToVerify() {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getIsCommentAccepted() == null)
                .collect(Collectors.toList());
    }
}

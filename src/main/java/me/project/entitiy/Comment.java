package me.project.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID commentId;
    private String commentText;
    private Boolean isCommentAccepted;

    @ManyToOne
    @JoinColumn(name = "my_user_id")
    @JsonBackReference
    private MyUser myUser;
}

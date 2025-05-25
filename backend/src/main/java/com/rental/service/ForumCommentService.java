package com.rental.service;

import com.rental.model.Forum;
import com.rental.model.ForumComment;
import com.rental.model.User;
import com.rental.repository.ForumCommentRepository;
import com.rental.repository.ForumRepository;
import com.rental.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumCommentService {

    private final ForumCommentRepository commentRepo;
    private final ForumRepository forumRepo;
    private final UserRepository userRepo;

    public ForumCommentService(ForumCommentRepository commentRepo, ForumRepository forumRepo, UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.forumRepo = forumRepo;
        this.userRepo = userRepo;
    }

    public ForumComment createComment(Integer postId, Long userId, String content) {
        Forum forum = forumRepo.findById(postId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        ForumComment comment = new ForumComment();
        comment.setForum(forum);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepo.save(comment);
    }

    public List<ForumComment> getCommentsByPostId(Integer postId) {
        return commentRepo.findByForumPostId(postId);
    }
}
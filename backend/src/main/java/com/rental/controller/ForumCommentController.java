package com.rental.controller;

import com.rental.model.ForumComment;
import com.rental.service.ForumCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class ForumCommentController {

    private final ForumCommentService commentService;

    public ForumCommentController(ForumCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ForumComment createComment(@RequestParam Integer postId,
                                      @RequestParam Long userId,
                                      @RequestParam String content) {
        return commentService.createComment(postId, userId, content);
    }

    @GetMapping("/{postId}")
    public List<ForumComment> getComments(@PathVariable Integer postId) {
        return commentService.getCommentsByPostId(postId);
    }
}

package com.rental.repository;

import com.rental.model.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Integer> {
    List<ForumComment> findByForumPostId(Integer postId);
}
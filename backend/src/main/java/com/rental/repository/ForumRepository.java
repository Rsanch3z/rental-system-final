package com.rental.repository;

import com.rental.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
}
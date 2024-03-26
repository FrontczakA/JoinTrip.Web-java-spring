package com.projekt.web.repository;

import com.projekt.web.models.Comment;
import com.projekt.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByUser(User user);
}

package com.projekt.web.repository;

import com.projekt.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long >  {
    User findByEmail(String email);
    User findByLogin(String login);
    @Query("SELECT c.user, COUNT(c) as commentCount " +
            "FROM Comment c " +
            "GROUP BY c.user " +
            "ORDER BY commentCount DESC")
    List<User> findMostActiveUsers();
    }


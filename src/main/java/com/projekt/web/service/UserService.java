package com.projekt.web.service;

import com.projekt.web.dto.RegisterDto;
import com.projekt.web.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(RegisterDto registerDto);
    User findByEmail(String email);
    User findByLogin(String login);
    List<User> getMostActiveUsers();

}

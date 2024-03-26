package com.projekt.web.service.implementation;

import com.projekt.web.dto.RegisterDto;
import com.projekt.web.models.Role;
import com.projekt.web.models.User;
import com.projekt.web.repository.CommentRepository;
import com.projekt.web.repository.RoleRepository;
import com.projekt.web.repository.UserRepository;
import com.projekt.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CommentRepository commentRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void saveUser(RegisterDto registerDto) {
        User user = new User();
        user.setLogin(registerDto.getLogin());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        Role role = roleRepository.findByName("user");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getMostActiveUsers() {
        List<User> mostActiveUsers = userRepository.findMostActiveUsers();
        return mostActiveUsers;
    }
}
package com.example.is_lab1;

import com.example.is_lab1.Post;
import com.example.is_lab1.User;
import com.example.is_lab1.PostRepository;
import com.example.is_lab1.UserRepository;
import com.example.is_lab1.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Value("${security.passwords.user}")
    private String userPass;

    @Override
    public void run(ApplicationArguments args) {
        // Создаём тестового пользователя, только если БД пуста
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(userPass);
            userService.save(user);
            System.out.println("Intialized 1 user: user");
        }

        if (postRepository.count() == 0) {
            Post post1 = new Post();
            post1.setTitle("Welcome to Secure API");
            post1.setContent("This is a protected post. Only authenticated users can see it.");
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setTitle("Security First");
            post2.setContent("Always hash passwords and validate tokens!");
            postRepository.save(post2);

            System.out.println("Intialized 2 posts");
        }
    }
}
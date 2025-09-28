package com.example.is_lab1;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

import com.example.is_lab1.Post;
import com.example.is_lab1.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private PostRepository postRepository;

    // GET /api/data — защищённый эндпоинт
    @GetMapping("/data")
    public List<Post> getData() {
        return postRepository.findAll();
    }

    // POST /api/posts — создание поста (тоже защищено)
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        // Защита от XSS: экранирование контента
        post.setTitle(escapeHtml4(post.getTitle()));
        post.setContent(escapeHtml4(post.getContent()));
        return postRepository.save(post);
    }

}
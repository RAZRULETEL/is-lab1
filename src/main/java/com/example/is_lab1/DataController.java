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
    public List<PostDTO> getData() {
        return postRepository.findAll().stream().map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent())).toList();
    }

    // POST /api/posts — создание поста (тоже защищено)
    @PostMapping("/post")
    public PostDTO createPost(@RequestBody PostDTO postData) {
        // Защита от XSS: экранирование контента
        Post post = new Post();
        post.setTitle(escapeHtml4(post.getTitle()));
        post.setContent(escapeHtml4(post.getContent()));
        postRepository.save(post);
        return new PostDTO(post.getId(), post.getTitle(), post.getContent());
    }

}
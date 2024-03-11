package de.stella.agora_web.posts.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.posts.controller.dto.PostDTO;
import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.posts.repository.PostRepository;
import de.stella.agora_web.posts.services.IPostService;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.repository.UserRepository;
import lombok.NonNull;

@RestController
@RequestMapping(path = "${api-endpoint}/posts")

public class PostController {

    private final IPostService postService;

    @Autowired
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create") 
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> createPost(@SuppressWarnings("rawtypes") @RequestBody PostDTO  postDTO) {
        Post post = postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public List<Post> index() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@NonNull @PathVariable("id") Long id) {
        Post post = postService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping("/store") 
    public ResponseEntity<Post> store(@SuppressWarnings("rawtypes") @RequestBody PostDTO postDTO) {
        Post post = postService.save(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") Long id, @SuppressWarnings("rawtypes") @RequestBody PostDTO postDTO) {
        Post post = postService.update(postDTO, id);
        return ResponseEntity.accepted().body(post);
    }


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Optional<User> user = userRepository.findByUsername("usuario");
        post.setAuthor(user);
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }
}
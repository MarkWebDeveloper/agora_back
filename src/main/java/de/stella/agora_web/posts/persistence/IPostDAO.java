package de.stella.agora_web.posts.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.stella.agora_web.posts.model.Post;
@Component
public interface IPostDAO {
        List<Post> findAll();
        Optional<Post> findById(Long id);
        List<Post> findByPostname(String postname); // Agrega este método
        Post save(Post post);
        void update(Post post, Post newPost);
        void deleteById(Long id);
    }
package de.stella.agora_web.posts.persistence.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.stella.agora_web.posts.exceptions.PostNotFoundException;
import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.posts.persistence.IPostDAO;
import de.stella.agora_web.posts.repository.PostRepository;

@Component
public class PostDAOImpl implements IPostDAO {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        Objects.requireNonNullElse(id, 0L);
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findByPostname(String postname) {
        return postRepository.findByPostname(postname);
    }

    @Override
    public Post save(Post post) {
        Objects.requireNonNull(post, "Post cannot be null");
        return postRepository.save(post);
    }

    @Override
    public void update(Post post, Post updatedPost) {
        Objects.requireNonNull(post, "Post cannot be null");
        Objects.requireNonNull(updatedPost, "Updated post cannot be null");

        post.setPostname(updatedPost.getPostname());
        post.setContent(updatedPost.getContent());
        save(post);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteById(Long id) {
        Objects.requireNonNullElse(id, 0L);
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }

}
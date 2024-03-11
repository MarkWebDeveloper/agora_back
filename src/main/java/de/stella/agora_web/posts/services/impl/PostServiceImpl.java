package de.stella.agora_web.posts.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.stella.agora_web.posts.controller.dto.PostDTO;
import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.posts.repository.PostRepository;
import de.stella.agora_web.posts.services.IPostService;
import de.stella.agora_web.user.model.User;



@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Post createPost(@SuppressWarnings("rawtypes") PostDTO postDTO, User user) {
        Post post = new Post();
        // Asignar valores de postDTO al post
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, @SuppressWarnings("rawtypes") PostDTO postDTO) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            // Actualizar valores de existingPost con los datos de postDTO
            return postRepository.save(existingPost);
        }
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Post save(@SuppressWarnings("rawtypes") PostDTO postDTO) {
        Post post = new Post();
        // Asignar valores de postDTO al post
        return postRepository.save(post);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post update(@SuppressWarnings("rawtypes") PostDTO postDTO, Long id) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            // Actualizar valores de existingPost con los datos de postDTO
            return postRepository.save(existingPost);
        }
        return null;
    }

    @Override
    public Post createPost(@SuppressWarnings("rawtypes") PostDTO postDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'createPost'");
    }
}

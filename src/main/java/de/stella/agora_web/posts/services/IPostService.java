package de.stella.agora_web.posts.services;



import java.util.List;

import de.stella.agora_web.posts.controller.dto.PostDTO;
import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.user.model.User;
import lombok.NonNull;




public interface IPostService {
  

    List<Post> getAllPosts();
    Post getPostById(Long id);
    @SuppressWarnings("rawtypes")
    Post createPost(PostDTO postDTO, User user);
    @SuppressWarnings("rawtypes")
    Post updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);



    Post save(@SuppressWarnings("rawtypes") PostDTO postDTO);

    void deleteById(Long id);

    @SuppressWarnings("rawtypes")
    Post update(PostDTO postDTO, Long id);
    Post createPost(@SuppressWarnings("rawtypes") PostDTO postDTO);
    Post getById(@NonNull Long id);
}
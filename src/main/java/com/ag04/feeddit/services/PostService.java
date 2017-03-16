package com.ag04.feeddit.services;


import com.ag04.feeddit.domain.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    Iterable<Post> listAllPosts();

    Post getPostById(Long id);

    Post savePost(Post post);

    void deletePost(Long id);
}
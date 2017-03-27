package com.ag04.feeddit.web.rest;

import com.ag04.feeddit.domain.Post;
import com.ag04.feeddit.domain.User;
import com.ag04.feeddit.repositories.PostRepository;
import com.ag04.feeddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marko on 27.03.17..
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private static final String ENTITY_NAME = "post";

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    PostResource(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("currentUser/upVotes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Set getUserUpVoteIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
        if (!user.getUpvotedPostsIds().isEmpty()) {
            return user.getUpvotedPostsIds();
        } else {
            return new HashSet();
        }
    }

    @GetMapping("currentUser/downVotes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Set getUserDownVoteIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
        if (!user.getDownvotedPostsIds().isEmpty()) {
            return user.getDownvotedPostsIds();
        } else {
            return new HashSet();
        }
    }

    @PutMapping("post/{id}/upVote")
    public ResponseEntity<Post> upVotePost(@PathVariable Long id) throws URISyntaxException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
        Set<Long> upvotedPostsIds = user.getUpvotedPostsIds();
        Set<Long> downvotedPostsIds = user.getDownvotedPostsIds();
        Post post = postRepository.findOne(id);
        Integer postUpvotes = post.getNumberOfUpvotes();

        if (upvotedPostsIds == null) {
            upvotedPostsIds = new HashSet<>();
        }

        if (downvotedPostsIds == null) {
            downvotedPostsIds = new HashSet<>();
        }

        if (upvotedPostsIds.contains(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            post.setNumberOfUpvotes(++postUpvotes);
            upvotedPostsIds.add(id);
            user.setUpvotedPostsIds(upvotedPostsIds);
        }
        if (downvotedPostsIds.contains(id)) {
            downvotedPostsIds.remove(id);
            if (post.getNumberOfUpvotes() == 0) {
                post.setNumberOfUpvotes(++postUpvotes);
            }
            user.setDownvotedPostsIds(downvotedPostsIds);
        }
        postRepository.save(post);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
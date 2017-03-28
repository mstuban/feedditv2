package com.ag04.feeddit.controllers;

import com.ag04.feeddit.domain.Post;
import com.ag04.feeddit.domain.User;
import com.ag04.feeddit.domain.UserRole;
import com.ag04.feeddit.services.PostService;
import com.ag04.feeddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by marko on 15.03.17..
 */

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    private List<Long> postsToBeDeleted = new ArrayList<>();


    @RequestMapping("post/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "postshow";
    }

    @RequestMapping("posts")
    public String showAllPosts(Model model) {
        List<Post> allPosts = new ArrayList<>();
        postService.listAllPosts().forEach(allPosts::add);
        model.addAttribute("listOfPosts", allPosts);
        return "posts";
    }


    @RequestMapping("post/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "post";
    }


    @RequestMapping("post/new")
    public String newPost(Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUserUsername", user.getUserName());
        model.addAttribute("post", new Post());
        return "post";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String savePost(Post post, Model model) {

        if (post.getNumberOfUpvotes() != 0) {
            return "redirect:/403";
        }

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<Post> posts = user.getPosts();
        posts.add(post);
        user.setPosts(posts);
        post.setUser(user);
        postService.savePost(post);
        userService.saveOrUpdate(user);
        return "redirect:/posts";
    }

    @RequestMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<Long> upvotedPostsIds = user.getUpvotedPostsIds();
        Set<Long> downvotedPostsIds = user.getDownvotedPostsIds();

        if (upvotedPostsIds == null) {
            upvotedPostsIds = new HashSet<>();
        }

        if (downvotedPostsIds == null) {
            downvotedPostsIds = new HashSet<>();
        }

        if (upvotedPostsIds.contains(id)) {
            upvotedPostsIds.remove(id);
        }

        if (downvotedPostsIds.contains(id)) {
            downvotedPostsIds.remove(id);
        }

        postService.deletePost(id);
        return "redirect:/posts";
    }

    @RequestMapping("/myPosts")
    public String getMyPosts(Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        List<Post> posts = new ArrayList<>();
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.addAll(user.getRoles());

        if (userRoles.get(0).getRole().equals("ROLE_ADMIN")) {
            postService.listAllPosts().forEach(posts::add);
        } else {
            posts.addAll(user.getPosts());
        }

        List<Long> checkboxList = new ArrayList<>();
        for (Post post : posts) {
            checkboxList.add(post.getId());
        }

        model.addAttribute("checkboxList", checkboxList);

        model.addAttribute("userPosts", posts);


        return "myposts";
    }

    @RequestMapping("/post/{id}/upVote")
    public String upvotePost(@PathVariable Long id, Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<Long> upvotedPostsIds = user.getUpvotedPostsIds();
        Set<Long> downvotedPostsIds = user.getDownvotedPostsIds();
        Post post = postService.getPostById(id);

        Integer postUpvotes = post.getNumberOfUpvotes();

        if (upvotedPostsIds == null) {
            upvotedPostsIds = new HashSet<>();
        }

        if (downvotedPostsIds == null) {
            downvotedPostsIds = new HashSet<>();
        }

        if (upvotedPostsIds.contains(id)) {
            return "posts";
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
        postService.savePost(post);
        userService.saveOrUpdate(user);
        List<Post> allPosts = new ArrayList<>();
        postService.listAllPosts().forEach(allPosts::add);
        model.addAttribute("listOfPosts", allPosts);

        return "redirect:/posts";
    }

    @RequestMapping("/post/{id}/downVote")
    public String downvotePost(@PathVariable Long id, Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<Long> upvotedPostsIds = user.getUpvotedPostsIds();
        Set<Long> downvotedPostsIds = user.getDownvotedPostsIds();
        Post post = postService.getPostById(id);

        Integer postUpvotes = post.getNumberOfUpvotes();

        if (upvotedPostsIds == null) {
            upvotedPostsIds = new HashSet<>();
        }

        if (downvotedPostsIds == null) {
            downvotedPostsIds = new HashSet<>();
        }

        if (downvotedPostsIds.contains(id)) {
            return "posts";
        } else {
            post.setNumberOfUpvotes(--postUpvotes);
            downvotedPostsIds.add(id);
            user.setDownvotedPostsIds(downvotedPostsIds);
        }
        if (upvotedPostsIds.contains(id)) {
            upvotedPostsIds.remove(id);
            if (post.getNumberOfUpvotes() == 0) {
                post.setNumberOfUpvotes(--postUpvotes);
            }
            user.setUpvotedPostsIds(upvotedPostsIds);
        }
        postService.savePost(post);
        userService.saveOrUpdate(user);
        List<Post> allPosts = new ArrayList<>();
        postService.listAllPosts().forEach(allPosts::add);
        model.addAttribute("listOfPosts", allPosts);

        return "redirect:/posts";
    }

    @RequestMapping("/post/addToIdArray/{id}")
    public String addToIdArray(@PathVariable Long id, Model model) {

        List<Post> allPosts = new ArrayList<>();
        postService.listAllPosts().forEach(allPosts::add);

        if (allPosts.contains(postService.getPostById(id))) {
            if (!postsToBeDeleted.contains(id)) {
                postsToBeDeleted.add(id);
            } else {
                postsToBeDeleted.remove(id);
            }
        }

        System.out.println("POSTS TO BE DELETED : " + postsToBeDeleted);

        model.addAttribute("userPosts", allPosts);

        return "redirect:/myposts";
    }

    @RequestMapping("/deletePosts")
    public String deletePosts(Model model) {
        List<Post> allPostsBeforeDeleting = new ArrayList<>();
        List<Post> allPostsAfterDeleting = new ArrayList<>();
        postService.listAllPosts().forEach(allPostsBeforeDeleting::add);

        for (Post post : allPostsBeforeDeleting) {
            if (postsToBeDeleted.contains(post.getId())) {
                postService.deletePost(post.getId());
            }
        }

        postService.listAllPosts().forEach(allPostsAfterDeleting::add);
        model.addAttribute("userPosts", allPostsAfterDeleting);

        return "redirect:/myposts";
    }

    @RequestMapping(value = "/checkboxes/select", method = RequestMethod.POST)
    public String actionStart(@RequestParam("command") Object command, Model model) {

        System.out.println(command);

        return null;
    }


    @RequestMapping("/403")
    public String getForbiddenPage() {
        return "403";
    }

}

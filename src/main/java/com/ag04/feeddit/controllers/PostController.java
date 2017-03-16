package com.ag04.feeddit.controllers;

import com.ag04.feeddit.domain.Post;
import com.ag04.feeddit.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko on 15.03.17..
 */

@Controller
public class PostController {

    @Autowired
    PostService postService;


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
        model.addAttribute("post", new Post());
        return "post";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String savePost(Post post) {
        postService.savePost(post);
        return "redirect:/post/" + post.getId();
    }

    @RequestMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

}

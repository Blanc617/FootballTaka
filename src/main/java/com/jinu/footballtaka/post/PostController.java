package com.jinu.footballtaka.post;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.jinu.footballtaka.post.domain.Post;
import com.jinu.footballtaka.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
    
    private final PostService postService;
    
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list-view")
    public String listView(@RequestParam(value = "boardType", required = false, defaultValue = "free") String boardType, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }

        List<Post> posts = postService.getPostListByCategory(userId, boardType);
        model.addAttribute("posts", posts);
        model.addAttribute("boardType", boardType);
        return "post/list";
    }

    @GetMapping("/create-view")
    public String showPostInputForm(Model model) {
        model.addAttribute("boardTypes", List.of("free", "domestic", "international"));
        return "post/input"; 
    }
    
    @GetMapping("/detail-view")
    public String showPostDetail(@RequestParam("id") Integer id, Model model) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        
        Post post = postService.getPost(id);
        
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        model.addAttribute("post", post);
        return "post/detail"; 
    }

    @GetMapping("/list-view/free")
    public String listFreePosts(Model model, HttpSession session) {
        return listPostsByCategory("free", model, session);
    }

    @GetMapping("/list-view/domestic")
    public String listDomesticPosts(Model model, HttpSession session) {
        return listPostsByCategory("domestic", model, session);
    }

    @GetMapping("/list-view/international")
    public String listInternationalPosts(Model model, HttpSession session) {
        return listPostsByCategory("international", model, session);
    }

    private String listPostsByCategory(String category, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Post> postList = postService.getPostListByCategory(userId, category);
        model.addAttribute("posts", postList); 
        model.addAttribute("boardType", category); 
        return "post/list"; 
    }
}

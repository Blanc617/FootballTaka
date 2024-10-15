package com.jinu.footballtaka.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/edit/{id}")
    public String showPostEditForm(@PathVariable("id") int id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; 
        }

        Post post = postService.getPost(id);
        if (post == null || post.getUserId() != userId) {
            return "redirect:/post/list-view/free"; 
        }

        model.addAttribute("post", post); 
        model.addAttribute("boardType", post.getBoardType()); 
        return "post/input"; 
    }

    @GetMapping("/detail-view/{boardType}/{id}")
    public String showPostDetail(
            @PathVariable("boardType") String boardType,
            @PathVariable("id") int id,
            Model model,
            HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }

        Post post = postService.getPost(id);
        
        model.addAttribute("post", post);
        model.addAttribute("boardType", boardType);
        session.setAttribute("boardType", boardType); 

        return "post/detail"; 
    }



    @GetMapping("/create-view")
    public String showPostInputForm(Model model) {
        model.addAttribute("boardTypes", List.of("free", "domestic", "international"));
        return "post/input"; 
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

package com.jinu.footballtaka.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String listView(Model model) {
	    List<Post> posts = postService.getAllPosts(); 
	    model.addAttribute("posts", posts);
	    return "post/list"; 
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
	    model.addAttribute("postList", postList); 
	    model.addAttribute("category", category); 
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
			return "redirect:/post/list-view/free"; 
		}
		
		Post post = postService.getPost(id);
		
		if (post == null) {
			return "redirect:/post/list-view/free";
		}

		model.addAttribute("post", post);
		return "post/detail"; 
	}
}

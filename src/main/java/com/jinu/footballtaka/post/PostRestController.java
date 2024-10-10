package com.jinu.footballtaka.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jinu.footballtaka.post.domain.Post;
import com.jinu.footballtaka.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
    
    private final PostService postService;
    
    public PostRestController(PostService postService) {
        this.postService = postService;
    }
    
    @PostMapping("/create")
    public Map<String, String> createMemo(
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam("boardType") String boardType,
            @RequestParam(value="imageFile", required=false) MultipartFile file,
            HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        Map<String, String> resultMap = new HashMap<>();
        
        if (userId == null) {
            resultMap.put("result", "fail");
            resultMap.put("message", "로그인이 필요합니다.");
            return resultMap;
        }
        
        Post post = postService.addPost(userId, title, contents, boardType, file);
        
        if (post != null) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
            resultMap.put("message", "게시글 저장 실패");
        }
        
        return resultMap;
    }
}

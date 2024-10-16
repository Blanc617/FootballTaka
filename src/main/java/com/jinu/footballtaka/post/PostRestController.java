package com.jinu.footballtaka.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            @RequestParam(value = "imageFile", required = false) MultipartFile file,
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
            resultMap.put("redirectUrl", "/post/list-view/" + boardType); 
        } else {
            resultMap.put("result", "fail");
            resultMap.put("message", "게시글 저장 실패");
        }
        
        return resultMap;
    }

    
    @DeleteMapping("/delete")
    public Map<String, String> deletePost(
            @RequestParam(value = "id", required = false, defaultValue = "0") int id,
            @RequestParam("boardType") String boardType) {
        
        System.out.println("Received boardType: " + boardType);
        System.out.println("Delete request received for ID: " + id);
        Map<String, String> resultMap = new HashMap<>();
        
        if (id == 0) {
            resultMap.put("result", "fail");
            resultMap.put("message", "유효하지 않은 게시글 ID입니다.");
            return resultMap;
        }

        if (postService.deletePost(id)) {
            resultMap.put("result", "success");
            resultMap.put("boardType", boardType); 
        } else {
            resultMap.put("result", "fail");
            resultMap.put("message", "게시글 삭제에 실패했습니다."); 
        }
        
        return resultMap;
    }





    @PutMapping("/update")
    public Map<String, Object> updatePost(
            @RequestParam("id") int id,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam(value = "boardType", required = false) String boardType,
            @RequestParam(value = "imageFile", required = false) MultipartFile file) {
        
        Map<String, Object> resultMap = new HashMap<>();
        
        Post updatedPost = postService.updatePost(id, title, contents, boardType, file);
        
        if (updatedPost != null) {
            resultMap.put("result", "success");
            resultMap.put("boardType", updatedPost.getBoardType());
            resultMap.put("postId", updatedPost.getId());
        } else {
            resultMap.put("result", "fail");
            resultMap.put("message", "게시글 수정 실패");
        }
        
        return resultMap;
    }



}

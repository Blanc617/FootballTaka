package com.jinu.footballtaka.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jinu.footballtaka.common.hash.FileManager;
import com.jinu.footballtaka.post.domain.Post;
import com.jinu.footballtaka.post.repository.PostRepository;

@Service
public class PostService {
	
	private final PostRepository postRepository; 
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post addPost(int userId, String title, String contents, String boardType, MultipartFile file) {
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
				.userId(userId)
				.title(title)
				.contents(contents)
				.boardType(boardType)  
				.imagePath(urlPath)
				.build();

		return postRepository.save(post); 
	}
	
	public List<Post> getPostList(int userId) {
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
	    Optional<Post> optionalPost = postRepository.findById(id);
	    if (optionalPost.isEmpty()) {
	        System.out.println("게시글 ID " + id + "에 해당하는 게시글이 없습니다.");
	        return null;
	    }
	    return optionalPost.get();
	}

	public List<Post> getPostListByCategory(Integer userId, String category) {
		return postRepository.findByUserIdAndBoardTypeOrderByIdDesc(userId, category);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll(); 
	}

	public boolean deletePost(int postId) {
	    try {
	        postRepository.deleteById(postId);
	        return true; 
	    } catch (EmptyResultDataAccessException e) {
	        System.err.println("Post with ID " + postId + " does not exist.");
	        return false; 
	    }
	}

	public Post updatePost(int postId, String title, String contents, String boardType, MultipartFile file) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.setTitle(title);
			post.setContents(contents);
			post.setBoardType(boardType);
			if (file != null && !file.isEmpty()) {
				String urlPath = FileManager.saveFile(post.getUserId(), file);
				post.setImagePath(urlPath);
			}
			return postRepository.save(post);
		}
		return null;
	}
}

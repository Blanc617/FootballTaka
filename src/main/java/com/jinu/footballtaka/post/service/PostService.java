package com.jinu.footballtaka.post.service;

import java.util.List;
import java.util.Optional;

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
		return postRepository.findById(id).orElse(null);
	}

	public List<Post> getPostListByCategory(Integer userId, String category) {
		return postRepository.findByUserIdAndBoardTypeOrderByIdDesc(userId, category);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll(); 
	}
}
package com.jinu.footballtaka.home;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//import com.jinu.footballtaka.dto.PostSummaryDto;
import com.jinu.footballtaka.home.service.HomeService;

@Controller
public class HomeController {

//    private final HomeService homeService;

//    public HomeController(HomeService homeService) {
//        this.homeService = homeService;
//    }

    @GetMapping("/home")
    public String home(Model model) {
        // 자유게시판, 국내축구, 해외축구 최신 게시글을 가져옴
//        List<PostSummaryDto> freePosts = homeService.getRecentPostsByCategory("Free");
//        List<PostSummaryDto> domesticPosts = homeService.getRecentPostsByCategory("Domestic");
//        List<PostSummaryDto> internationalPosts = homeService.getRecentPostsByCategory("International");

        // 모델에 게시글 리스트 추가
//        model.addAttribute("freePosts", freePosts);
//        model.addAttribute("domesticPosts", domesticPosts);
//        model.addAttribute("internationalPosts", internationalPosts);

        // 홈 화면으로 이동
        return "post/home";
    }
}

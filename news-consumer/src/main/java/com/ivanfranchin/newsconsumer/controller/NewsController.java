package com.ivanfranchin.newsconsumer.controller;

import com.ivanfranchin.newsconsumer.client.NewsProducerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class NewsController {

    private final NewsProducerClient newsProducerClient;

    @GetMapping(value = {"/", "/news"})
    public String getNews(Model model) {
        model.addAttribute("newsList", newsProducerClient.getNews());
        return "news";
    }
}

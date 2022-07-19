package com.ivanfranchin.newsconsumer.controller;

import com.ivanfranchin.newsconsumer.client.NewsProducerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
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

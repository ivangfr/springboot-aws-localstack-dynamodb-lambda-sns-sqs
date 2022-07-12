package com.mycompany.consumerservice.controller;

import com.mycompany.consumerservice.client.ProducerServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NewsController {

    private final ProducerServiceClient producerServiceClient;

    @GetMapping(value = {"/", "/news"})
    public String getNews(Model model) {
        model.addAttribute("newsList", producerServiceClient.getNews());
        return "news";
    }
}

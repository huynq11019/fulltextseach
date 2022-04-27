package com.example.hibernatesearch.presentation.web;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;
import com.example.hibernatesearch.inftrastructure.persistence.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class SearchController {

    @Autowired
    private PostRepository repository;

    @RequestMapping(value = "/")
    public String search(@RequestParam(value = "search", required = false) String q, Model model) {
        log.info("Searching for {}", q);
        List<Post> searchResults = null;
        try {
            searchResults = repository.search(q);
            log.info("Search results: {}", searchResults);
        } catch (Exception ex) {
            // Nothing
            ex.printStackTrace();
        }
        model.addAttribute("search", searchResults);
        return "index";

    }
}

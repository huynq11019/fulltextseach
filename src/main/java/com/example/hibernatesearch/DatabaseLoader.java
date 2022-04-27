package com.example.hibernatesearch;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;
import com.example.hibernatesearch.inftrastructure.persistence.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
@Slf4j
//@Component
public class DatabaseLoader implements CommandLineRunner {
    private final PostRepository repository;

    @Autowired
    public DatabaseLoader(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String fakeApiUrl = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<String> responseEntity = restTemplate.exchange(fakeApiUrl, HttpMethod.GET, null, String.class);

        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(responseEntity.getBody()));
        log.info("jsonArray: {}", jsonArray);
        int length = jsonArray.length();

        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Post post = new Post();
            post.setTitle(jsonObject.getString("title"));
            post.setContent(jsonObject.getString("body"));
            repository.save(post);
        }
    }
}

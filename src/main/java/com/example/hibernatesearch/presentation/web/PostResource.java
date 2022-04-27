package com.example.hibernatesearch.presentation.web;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;
import com.example.hibernatesearch.inftrastructure.persistence.repository.PostRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/posts")
public class PostResource {
    private final PostRepository postRepository;

    public PostResource(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/insert")
    public ResponseEntity<String> insert() {
        RestTemplate restTemplate = new RestTemplate();
        String fakeApiUrl = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<String> responseEntity = restTemplate.exchange(fakeApiUrl, HttpMethod.GET, null, String.class);

        JSONArray jsonArray = new JSONArray(Objects.requireNonNull(responseEntity.getBody()));
        int length = jsonArray.length();

        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Post post = new Post();
            post.setTitle(jsonObject.getString("title"));
            post.setContent(jsonObject.getString("body"));
            postRepository.save(post);
        }
        return ResponseEntity.ok("success");
    }
}

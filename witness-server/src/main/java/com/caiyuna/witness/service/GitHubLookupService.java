package com.caiyuna.witness.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.caiyuna.witness.entity.User;

/**
 * @author Ldl
 */
@Service
public class GitHubLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplate restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder;
    }

    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        LOGGER.info("Looking up" + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);

        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

}

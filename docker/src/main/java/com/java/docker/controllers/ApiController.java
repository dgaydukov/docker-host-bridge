package com.java.docker.controllers;

import com.java.docker.dto.ApiInfo;
import com.java.docker.dto.ResponseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ApiController {
    private final static String APP_NAME = "docker";
    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    @GetMapping("/info")
    public ApiInfo getStatus(){
        return new ApiInfo(APP_NAME, LocalDateTime.now());
    }


    @PostMapping("/call")
    public ResponseInfo callDocker(){
        log.info("Calling Host API");
        try {
            ApiInfo info = restTemplate.getForObject(baseUrl, ApiInfo.class);
            return new ResponseInfo(APP_NAME, info.toString(), LocalDateTime.now(), null);
        } catch (Exception ex) {
            log.error("Failed to fetch data: url={}", baseUrl, ex);
            return new ResponseInfo(APP_NAME, null, LocalDateTime.now(), ex.getMessage());
        }
    }
}
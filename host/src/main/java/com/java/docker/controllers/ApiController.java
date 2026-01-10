package com.java.docker.controllers;

import com.java.docker.dto.ApiInfo;
import com.java.docker.dto.ResponseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RestTemplate restTemplate;

    @GetMapping("/info")
    public ApiInfo getStatus(){
        return new ApiInfo("host", LocalDateTime.now());
    }


    @PostMapping("/call")
    public ResponseInfo callDocker(){
        log.info("Calling Docker API");
        String url = "http://127.0.0.1:4444/api/info";
        try {
            ApiInfo info = restTemplate.getForObject(url, ApiInfo.class);
            return new ResponseInfo("host", info.toString(), LocalDateTime.now(), null);
        } catch (Exception ex) {
            log.error("Failed to fetch data: url={}", url, ex);
            return new ResponseInfo("host", null, LocalDateTime.now(), ex.getMessage());
        }
    }
}
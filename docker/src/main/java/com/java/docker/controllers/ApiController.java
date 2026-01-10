package com.java.docker.controllers;

import com.java.docker.dto.Info;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/info")
    public Info getStatus(){
        return new Info("docker", LocalDateTime.now());
    }
}
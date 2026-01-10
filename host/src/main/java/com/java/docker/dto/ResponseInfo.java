package com.java.docker.dto;

import java.time.LocalDateTime;

public record ResponseInfo(String app, String response, LocalDateTime time, String error) {
}

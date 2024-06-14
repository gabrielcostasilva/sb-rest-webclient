package com.example.webclient;

public record Todo(
    Integer id,
    Integer userId,
    String title,
    Boolean completed
) {}

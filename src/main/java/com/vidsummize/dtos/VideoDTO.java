package com.vidsummize.dtos;

import java.time.LocalDateTime;

public record VideoDTO(
        Integer id,
        String youtubeUrl,
        String transcription,
        String summary,
        LocalDateTime createdAt
) {}

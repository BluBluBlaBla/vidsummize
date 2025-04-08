package com.vidsummize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private Integer id;
    private String youtubeUrl;
    private String transcription;
    private String summary;
    private LocalDateTime createdAt;
}
package com.vidsummize.services;

import com.vidsummize.dtos.CreateVideoDTO;
import com.vidsummize.dtos.VideoDTO;

public interface VideoService {
    VideoDTO transcribeVideo(CreateVideoDTO createVideoDTO);
    String summarizeText(String transcription);
}
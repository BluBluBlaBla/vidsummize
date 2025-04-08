package com.vidsummize.controllers;

import com.vidsummize.dtos.CreateVideoDTO;
import com.vidsummize.dtos.VideoDTO;
import com.vidsummize.services.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<VideoDTO> transcribeVideo(@RequestBody CreateVideoDTO createVideoDTO) {
        VideoDTO videoDTO = videoService.transcribeVideo(createVideoDTO);
        return ResponseEntity.ok(videoDTO);
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeText(@RequestBody Map<String, String> request) {
        String transcription = request.get("transcription");
        if (transcription == null || transcription.isEmpty()) {
            return ResponseEntity.badRequest().body("Transcrição é obrigatória.");
        }
        String summary = videoService.summarizeText(transcription);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Integer id) {
        VideoDTO videoDTO = videoService.findById(id);
        return ResponseEntity.ok(videoDTO);
    }
}
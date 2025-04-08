package com.vidsummize.services;

import com.vidsummize.dtos.CreateVideoDTO;
import com.vidsummize.dtos.VideoDTO;
import com.vidsummize.integrations.DeepSeekIntegration;
import com.vidsummize.integrations.WhisperIntegration;
import com.vidsummize.models.Video;
import com.vidsummize.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final WhisperIntegration whisperIntegration;
    private final DeepSeekIntegration deepSeekIntegration;

    public VideoServiceImpl(VideoRepository videoRepository,
                            WhisperIntegration whisperIntegration,
                            DeepSeekIntegration deepSeekIntegration) {
        this.videoRepository = videoRepository;
        this.whisperIntegration = whisperIntegration;
        this.deepSeekIntegration = deepSeekIntegration;
    }

    @Override
    public VideoDTO transcribeVideo(CreateVideoDTO createVideoDTO) {
        String transcription = whisperIntegration.transcribe(createVideoDTO.youtubeUrl());

        Video video = Video.builder()
                .youtubeUrl(createVideoDTO.youtubeUrl())
                .transcription(transcription)
                .createdAt(LocalDateTime.now())
                .build();

        Video savedVideo = videoRepository.save(video);

        return new VideoDTO(
                savedVideo.getId(),
                savedVideo.getYoutubeUrl(),
                savedVideo.getTranscription(),
                savedVideo.getSummary(),
                savedVideo.getCreatedAt()
        );
    }

    @Override
    public String summarizeText(String transcription) {
        return deepSeekIntegration.summarize(transcription);
    }
}
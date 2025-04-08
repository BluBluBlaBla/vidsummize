package com.vidsummize.services;

import com.vidsummize.dtos.CreateVideoDTO;
import com.vidsummize.dtos.VideoDTO;
import com.vidsummize.integrations.DeepSeekIntegration;
import com.vidsummize.integrations.WhisperIntegration;
import com.vidsummize.models.Video;
import com.vidsummize.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final WhisperIntegration whisperIntegration;
    private final DeepSeekIntegration deepSeekIntegration;
    private final ExecutorService executorService;


    public VideoServiceImpl(VideoRepository videoRepository,
                            WhisperIntegration whisperIntegration,
                            DeepSeekIntegration deepSeekIntegration,
                            @Qualifier("transcriptionExecutorService") ExecutorService executorService
    ) {
        this.videoRepository = videoRepository;
        this.whisperIntegration = whisperIntegration;
        this.deepSeekIntegration = deepSeekIntegration;
        this.executorService = executorService;
    }

    @Override
    public VideoDTO transcribeVideo(CreateVideoDTO createVideoDTO) {
        Video video = Video.builder()
                .youtubeUrl(createVideoDTO.youtubeUrl())
                .transcription(null)
                .summary(null)
                .createdAt(LocalDateTime.now())
                .build();

        Video savedVideo = videoRepository.save(video);

        executorService.submit(() -> {
            try {
                String transcription = whisperIntegration.transcribe(savedVideo.getYoutubeUrl());

                savedVideo.setTranscription(transcription);
                videoRepository.update(savedVideo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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
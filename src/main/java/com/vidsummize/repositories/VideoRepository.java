package com.vidsummize.repositories;

import com.vidsummize.models.Video;
import java.util.List;
import java.util.Optional;

public interface VideoRepository {
    Video save(Video video);
    Optional<Video> findById(Integer id);
    List<Video> findAll();
}

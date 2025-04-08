package com.vidsummize.repositories;

import com.vidsummize.models.Video;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import static com.vidsummize.jooq.tables.Video.VIDEO;

@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private final DSLContext dsl;

    public VideoRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Video save(Video video) {
        Integer id = dsl.insertInto(VIDEO)
                .set(VIDEO.YOUTUBE_URL, video.getYoutubeUrl())
                .set(VIDEO.TRANSCRIPTION, video.getTranscription())
                .set(VIDEO.SUMMARY, video.getSummary())
                .set(VIDEO.CREATED_AT, video.getCreatedAt())
                .returning(VIDEO.ID)
                .fetchOne()
                .getValue(VIDEO.ID);
        video.setId(id);
        return video;
    }

    public Optional<Video> findById(Integer id) {
        Video result = dsl.selectFrom(VIDEO)
                .where(VIDEO.ID.eq(id))
                .fetchOneInto(Video.class);
        return Optional.ofNullable(result);
    }

    @Override
    public List<Video> findAll() {
        return dsl.selectFrom(VIDEO)
                .fetchInto(Video.class);
    }
}

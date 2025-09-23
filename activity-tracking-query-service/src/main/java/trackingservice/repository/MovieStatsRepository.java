package trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trackingservice.views.MovieStatsView;

import java.util.UUID;

@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStatsView, UUID> {
    @Modifying
    @Query("UPDATE MovieStatsView m SET m.movieViews = m.movieViews + 1 WHERE m.movieId = :movieId")
    void incrementViews(@Param("movieId") UUID movieId);
}
package trackingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trackingservice.views.MovieStatsView;

import java.util.UUID;

@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStatsView, UUID> {}
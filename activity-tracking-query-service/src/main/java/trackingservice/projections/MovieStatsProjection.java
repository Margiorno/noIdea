package trackingservice.projections;

import com.pm.noidea.common.movie.events.MovieAddedEvent;
import com.pm.noidea.common.movie.events.ViewRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import trackingservice.service.MovieStatsService;

@Service
@RequiredArgsConstructor
public class MovieStatsProjection {
    private final MovieStatsService movieStatsService;

    @EventHandler
    public void on(MovieAddedEvent event) {
        movieStatsService.initMovieStats(event.getId());
    }

    @EventHandler
    public void on(ViewRegisteredEvent event){
        System.out.println(event.getId());

        movieStatsService.incrementMovieView(event.getId());
    }
}

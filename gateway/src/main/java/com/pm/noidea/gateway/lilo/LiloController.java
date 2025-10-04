package com.pm.noidea.gateway.lilo;

import com.pm.noidea.gateway.config.ServicesProperties;
import com.pm.noidea.gateway.lilo.IntrospectionRetrieverImpl;
import com.pm.noidea.gateway.lilo.IntrospectionRetrieverImplNoAuth;
import com.pm.noidea.gateway.lilo.QueryRetrieverImpl;
import com.pm.noidea.gateway.lilo.QueryRetrieverImplNoAuth;
import com.pm.noidea.gateway.service.JwtService;
import io.fria.lilo.GraphQLRequest;
import io.fria.lilo.Lilo;
import io.fria.lilo.RemoteSchemaSource;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LiloController {

    private static final String IDENTITY_SERVICE = "IDENTITY_SERVICE";
    private static final String MOVIE_CATALOG_SERVICE = "MOVIE_CATALOG_SERVICE";
    private static final String ACTIVITY_QUERY_SERVICE = "ACTIVITY_QUERY_SERVICE";
    private static final String ACTIVITY_COMMAND_SERVICE = "ACTIVITY_COMMAND_SERVICE";

    private final ServicesProperties servicesProperties;
    private final JwtService jwtService;

    private final Lilo lilo;

    public LiloController(ServicesProperties servicesProperties, JwtService jwtService) {
        this.servicesProperties = servicesProperties;
        this.jwtService = jwtService;

        this.lilo = Lilo.builder()
                .addSource(RemoteSchemaSource.create(
                        IDENTITY_SERVICE,
                        new IntrospectionRetrieverImplNoAuth(servicesProperties.getIdentityServiceUri()),
                        new QueryRetrieverImplNoAuth(servicesProperties.getIdentityServiceUri())))
                .addSource(RemoteSchemaSource.create(
                        MOVIE_CATALOG_SERVICE,
                        new IntrospectionRetrieverImpl(servicesProperties.getMovieCatalogServiceUri()),
                        new QueryRetrieverImpl(servicesProperties.getMovieCatalogServiceUri())))
                .addSource(RemoteSchemaSource.create(
                        ACTIVITY_QUERY_SERVICE,
                        new IntrospectionRetrieverImpl(servicesProperties.getActivityQueryServiceUri()),
                        new QueryRetrieverImpl(servicesProperties.getActivityQueryServiceUri())))
                .addSource(RemoteSchemaSource.create(
                        ACTIVITY_COMMAND_SERVICE,
                        new IntrospectionRetrieverImpl(servicesProperties.getActivityCommandServiceUri()),
                        new QueryRetrieverImpl(servicesProperties.getActivityCommandServiceUri())))
                .build();
    }

    @ResponseBody
    @PostMapping("/graphql")
    public @NotNull Map<String, Object> stitch(
            @RequestBody final @NotNull GraphQLRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) final String authorizationHeader) {

        String userId = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            userId = jwtService.extractUserId(token);
        }

        return this.lilo.stitch(request.toExecutionInput(userId)).toSpecification();
    }
}
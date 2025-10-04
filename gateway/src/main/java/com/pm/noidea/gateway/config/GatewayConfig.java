package com.pm.noidea.gateway.config;

import com.pm.noidea.gateway.retrievers.IntrospectionRetrieverImpl;
import com.pm.noidea.gateway.retrievers.IntrospectionRetrieverImplNoAuth;
import com.pm.noidea.gateway.retrievers.QueryRetrieverImpl;
import com.pm.noidea.gateway.retrievers.QueryRetrieverImplNoAuth;
import io.fria.lilo.Lilo;
import io.fria.lilo.RemoteSchemaSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    private static final String IDENTITY_SERVICE = "IDENTITY_SERVICE";
    private static final String MOVIE_CATALOG_SERVICE = "MOVIE_CATALOG_SERVICE";
    private static final String ACTIVITY_QUERY_SERVICE = "ACTIVITY_QUERY_SERVICE";
    private static final String ACTIVITY_COMMAND_SERVICE = "ACTIVITY_COMMAND_SERVICE";

    @Bean
    public Lilo lilo(ServicesProperties servicesProperties, WebClient.Builder webClientBuilder) {

        return Lilo.builder()
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
}
//package com.pm.noidea.gateway.config;
//
//import io.fria.lilo.Lilo;
//import io.fria.lilo.RemoteSchemaSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public Lilo lilo(ServicesProperties servicesProperties, WebClient.Builder webClientBuilder) {
//
//        return Lilo.builder()
//                .addSource(RemoteSchemaSource.create("IDENTITY_SERVICE", servicesProperties.getIdentityServiceUri()))
//                .addSource(RemoteSchemaSource.create("MOVIE_CATALOG_SERVICE", servicesProperties.getMovieCatalogServiceUri()))
//                .addSource(RemoteSchemaSource.create("ACTIVITY_QUERY_SERVICE", servicesProperties.getActivityQueryServiceUri()))
//                .addSource(RemoteSchemaSource.create("ACTIVITY_COMMAND_SERVICE", servicesProperties.getActivityCommandServiceUri()))
//                .build();
//    }
//}
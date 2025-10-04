package com.pm.noidea.gateway.retrievers;

import io.fria.lilo.LiloContext;
import io.fria.lilo.SchemaSource;
import io.fria.lilo.SyncIntrospectionRetriever;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class IntrospectionRetrieverImplNoAuth implements SyncIntrospectionRetriever {

    private final String schemaUrl;
    private final RestTemplate restTemplate;

    public IntrospectionRetrieverImplNoAuth(@NotNull String schemaUrl) {
        this.schemaUrl = schemaUrl;
        this.restTemplate =
                new RestTemplateBuilder()
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    }

    @Override
    public @NotNull String get(
            @NotNull LiloContext liloContext,
            @NotNull SchemaSource schemaSource,
            @NotNull String query,
            @Nullable Object localContext) {

        return Objects.requireNonNull(
                this.restTemplate.exchange(
                        this.schemaUrl,
                        HttpMethod.POST,
                        new HttpEntity<>(query, new HttpHeaders()),
                        String.class
                ).getBody()
        );
    }
}
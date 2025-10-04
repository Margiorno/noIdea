package com.pm.noidea.gateway.lilo;

import io.fria.lilo.GraphQLQuery;
import io.fria.lilo.LiloContext;
import io.fria.lilo.SchemaSource;
import io.fria.lilo.SyncQueryRetriever;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class QueryRetrieverImpl implements SyncQueryRetriever {

    private final String schemaUrl;
    private final RestTemplate restTemplate;

    public QueryRetrieverImpl(@NotNull String schemaUrl) {
        this.schemaUrl = schemaUrl;
        this.restTemplate = new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public @NotNull String get(
            @NotNull LiloContext liloContext,
            @NotNull SchemaSource schemaSource,
            @NotNull GraphQLQuery graphQLQuery,
            @Nullable Object localContext) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", (String) localContext);

        return Objects.requireNonNull(
                this.restTemplate.exchange(
                        this.schemaUrl,
                        HttpMethod.POST,
                        new HttpEntity<>(graphQLQuery.getQuery(), headers),
                        String.class
                ).getBody()
        );
    }
}

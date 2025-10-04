package com.pm.noidea.trackingservice.interceptors;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserIdInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String userId = request.getHeaders().getFirst("X-User-Id");

        if (userId != null) {
            request.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(context -> context.put("userId", userId)).build());
        }
        return chain.next(request);
    }
}

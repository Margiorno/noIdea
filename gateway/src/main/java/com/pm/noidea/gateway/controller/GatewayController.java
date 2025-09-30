package com.pm.noidea.gateway.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import io.fria.lilo.GraphQLRequest;
import io.fria.lilo.Lilo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GatewayController {
    private final Lilo lilo;

    @PostMapping("/graphql")
    public Map<String, Object> graphql(@RequestBody GraphQLRequest request) {
        return lilo.stitch(request.toExecutionInput()).toSpecification();
    }
}

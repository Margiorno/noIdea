package com.pm.noidea.gateway.controller;

import com.pm.noidea.gateway.service.JwtService;
import io.fria.lilo.GraphQLRequest;
import io.fria.lilo.Lilo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private final JwtService jwtService;
    private final Lilo lilo;

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
//package com.pm.noidea.gateway.controller;
//
//import com.google.common.net.HttpHeaders;
//import com.pm.noidea.gateway.service.JwtService;
//import graphql.ExecutionInput;
//import graphql.ExecutionResult;
//import io.fria.lilo.GraphQLRequest;
//import io.fria.lilo.Lilo;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class GatewayController {
//
//    private final Lilo lilo;
//    private final JwtService jwtService;
//
//    @ResponseBody
//    @PostMapping("/graphql")
//    public Map<String, Object> stitch(
//            @RequestBody GraphQLRequest request,
//            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//
//        String token = authorizationHeader.substring(7);
//
//        String userId = jwtService.extractUserId(token);
//
//        return this.lilo.stitch(request.toExecutionInput(userId)).toSpecification();
//    }
//}

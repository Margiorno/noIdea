package com.pm.noidea.identityservice.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @GraphQlExceptionHandler
    public GraphQLError handleInvalidCredentialsException(
            InvalidCredentialsException exception,
            DataFetchingEnvironment environment
    ) {
        return GraphqlErrorBuilder.newError()
                .message(exception.getMessage())
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .extensions(Map.of(
                        "errorCode", "INVALID_CREDENTIALS",
                        "classification", ErrorType.UNAUTHORIZED
                )).build();
    }
}

package ru.shaxowskiy.socialservice.services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shaxowskiy.cloudfilestorage.AuthServiceGrpc;
import ru.shaxowskiy.cloudfilestorage.JwtRequest;

@Service
public class AuthServiceImpl {

    private final Integer INDEX_START_TOKEN = 7;
    private final String target;

    public AuthServiceImpl(@Value("${target_grpc}") String target) {
        this.target = target;
    }


    public JwtRequest.ValidateTokenResponse validateTokenResponse(String fullToken){
        //String jwtToken = parseJwtToken(fullToken);
        String jwtToken = fullToken;
        //TODO вынести chanel в конфиг
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forTarget(target)
                .usePlaintext() //TODO В продакшене нужно .useTransportSecurity() (TLS).
                .build();

        AuthServiceGrpc.AuthServiceBlockingStub stub = AuthServiceGrpc.newBlockingStub(managedChannel);
        //TODO хардкод изменить на токен
        JwtRequest.ValidateTokenRequest request = JwtRequest.ValidateTokenRequest.newBuilder()
                .setToken(jwtToken)
                .build();

        //удаленный вызов validateToken
        JwtRequest.ValidateTokenResponse validateTokenResponse = stub.validateToken(request);
        managedChannel.shutdownNow();
        return validateTokenResponse;
    }

    private String parseJwtToken(String token) {
        return token.substring(INDEX_START_TOKEN);
    }
}

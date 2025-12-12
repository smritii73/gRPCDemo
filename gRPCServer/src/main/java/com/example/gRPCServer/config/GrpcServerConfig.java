package com.example.gRPCServer.config;

import com.example.gRPCServer.service.SampleServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class GrpcServerConfig {


    @Value("${grpc.server.port:9090}")
    private int grpcServerPort;

    private final SampleServiceImpl sampleServicesImpl;
    private Server server;

    public GrpcServerConfig(SampleServiceImpl sampleServicesImpl) {
        this.sampleServicesImpl = sampleServicesImpl;
    }

    @PostConstruct
    public void startGrpcServer() throws IOException {
        server=ServerBuilder
                .forPort(grpcServerPort)
                .addService(sampleServicesImpl)
                .build()
                .start();

        System.out.println("gRPC Server started on port "+  grpcServerPort);

        new Thread(()-> {
            try {
                if(server!=null) server.awaitTermination();
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.err.println("gRPC Server interrupted");
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("shutting down gRPC Server");
            if(server!=null) server.shutdown();
        }));
    }
}
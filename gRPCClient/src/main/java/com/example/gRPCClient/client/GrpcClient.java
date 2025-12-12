package com.example.gRPCClient.client;

import com.example.gRPCClient.SampleServiceGrpc;
import com.example.gRPCClient.SampleRequest;
import com.example.gRPCClient.SampleResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class GrpcClient {


    @Value("${grpc.server.port:9090}")
    private int grpcServerPort;

    @Value("${grpc.server.host:localhost}")
    private String grpcServerHost;

    private ManagedChannel channel;

    private SampleServiceGrpc.SampleServiceBlockingStub sampleServiceStub;

    @PostConstruct
    public void init(){
        channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                .usePlaintext().build();

        sampleServiceStub = SampleServiceGrpc.newBlockingStub(channel);
    }

    public String sayHello(String name){
        SampleRequest request = SampleRequest.newBuilder().setName(name).build();

        SampleResponse response = sampleServiceStub.saySampleHello(request); // This is the place where u make the grpc call
        return response.getMessage();
    }

}
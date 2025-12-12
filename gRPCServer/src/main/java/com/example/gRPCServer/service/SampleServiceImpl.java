package com.example.gRPCServer.service;

import com.example.gRPCServer.SampleRequest;
import com.example.gRPCServer.SampleResponse;
import com.example.gRPCServer.SampleServiceGrpc;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl extends SampleServiceGrpc.SampleServiceImplBase {

    @Override
    public void saySampleHello(SampleRequest request, StreamObserver<SampleResponse> responseObserver) {
        String name = request.getName();
        String message = "Hello, " + name + "! Welcome to the sample gRPC service.";

        SampleResponse response = SampleResponse.newBuilder().setMessage(message).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
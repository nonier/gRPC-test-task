package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.DigitSequenceServiceImpl;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new DigitSequenceServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}
package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.dto.ServerValue;
import org.example.observer.SequenceObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

public class ClientMain {

    private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
    private static final int ITERATIONS_COUNT = 50;

    public static void main(String[] args) {
        log.info("Starting...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        DigitSequenceServiceOuterClass.SequenceRequest request = DigitSequenceServiceOuterClass.SequenceRequest.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();
        DigitSequenceServiceGrpc.DigitSequenceServiceStub stub = DigitSequenceServiceGrpc.newStub(channel);
        ServerValue serverValue = new ServerValue(0L);
        stub.getSequence(request, new SequenceObserver(serverValue));
        long current = 0;
        Long prevValue = null;
        for (int i = 1; i <= ITERATIONS_COUNT; i++) {
            long value = serverValue.getValue();
            if (Objects.equals(prevValue, value)) {
                current = current + 1;
            } else {
                current = current + value + 1;
                prevValue = value;
            }
            log.info("Iterations left: {}, Current value: {}", ITERATIONS_COUNT - i, current);
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("End of iteration");
        channel.shutdown();
    }
}
package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.DigitSequenceServiceGrpc;
import org.example.DigitSequenceServiceOuterClass.SequenceRequest;
import org.example.DigitSequenceServiceOuterClass.SequenceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DigitSequenceServiceImpl extends DigitSequenceServiceGrpc.DigitSequenceServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(DigitSequenceServiceImpl.class);

    @Override
    public void getSequence(SequenceRequest request, StreamObserver<SequenceResponse> responseObserver) {
        log.info("Getting new request: firstValue: {}, lastValue: {}", request.getFirstValue(), request.getLastValue());
        long firstValue = request.getFirstValue();
        long lastValue = request.getLastValue();
        while (firstValue < lastValue) {
            SequenceResponse response = SequenceResponse.newBuilder()
                    .setValue(++firstValue)
                    .build();
            log.info("Passing new value: {}", firstValue);
            responseObserver.onNext(response);
            try {
                Thread.sleep(Duration.ofSeconds(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
        log.info("End of request processing");
    }
}
package org.example.observer;

import io.grpc.stub.StreamObserver;
import org.example.DigitSequenceServiceOuterClass.SequenceResponse;
import org.example.dto.ServerValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequenceObserver implements StreamObserver<SequenceResponse> {

    private static final Logger log = LoggerFactory.getLogger(SequenceObserver.class);

    private final ServerValue serverValue;

    public SequenceObserver(ServerValue serverValue) {
        this.serverValue = serverValue;
    }

    @Override
    public void onNext(SequenceResponse sequenceResponse) {
        long value = sequenceResponse.getValue();
        log.info("New server value: {}", value);
        serverValue.setValue(value);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error occurred: {}", throwable.toString());
    }

    @Override
    public void onCompleted() {
        log.info("Server response completed");
    }
}
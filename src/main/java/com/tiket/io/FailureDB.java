package com.tiket.io;

import java.time.Duration;
import java.util.Set;

public interface FailureDB {

    record Failure(String link, String api, String module, String vertical, String platform) { }
    record DBFailure(Failure failure, long timestamp) { }

    boolean insertFailure(DBFailure dbFailure);
    Set<Failure> getFailures(Duration duration);
}

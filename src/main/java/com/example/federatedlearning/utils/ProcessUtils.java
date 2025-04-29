package com.example.federatedlearning.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProcessUtils {

    public static Process startProcess(String... command) throws IOException {
        log.debug("Executing: {}", String.join(" ", command));
        return new ProcessBuilder(command).start();
    }

    public static void executeWithTimeout(String[] command, int timeoutSeconds)
            throws IOException, InterruptedException {
        Process process = startProcess(command);
        if (!process.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new IOException("Process timeout exceeded");
        }
        if (process.exitValue() != 0) {
            throw new IOException("Process failed with code: " + process.exitValue());
        }
    }

}

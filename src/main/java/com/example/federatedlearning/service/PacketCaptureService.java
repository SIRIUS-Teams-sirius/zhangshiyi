package com.example.federatedlearning.service;

import org.springframework.core.io.Resource;
import java.io.IOException;

public interface PacketCaptureService {
    void startCapture();
    void stopCapture();
    Resource captureToXlsxResource() throws IOException; // 返回Spring Resource抽象
    boolean isRunning();
}

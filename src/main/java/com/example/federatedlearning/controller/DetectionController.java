package com.example.federatedlearning.controller;

import com.example.federatedlearning.service.PacketCaptureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/capture")
@RequiredArgsConstructor
public class DetectionController {

    private final PacketCaptureService captureService;

    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        captureService.startCapture();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stop() {
        captureService.stopCapture();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> status() {
        return ResponseEntity.ok(captureService.isRunning());
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download() throws Exception {
        Resource resource = captureService.captureToXlsxResource();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("network_capture.xlsx")
                                .build()
                                .toString())
                .body(resource);
    }
}

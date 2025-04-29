package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.service.PacketCaptureService;
import com.example.federatedlearning.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class PacketCaptureServiceImpl implements PacketCaptureService {

    @Value("${capture.network-interface:eth0}")
    private String networkInterface;

    @Value("${capture.timeout-seconds:5}")
    private int captureTimeout;

    private final AtomicBoolean captureEnabled = new AtomicBoolean(false);
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public synchronized void startCapture() {
        if (captureEnabled.compareAndSet(false, true)) {
            log.info("Capture service activated");
        }
    }

    @Override
    public synchronized void stopCapture() {
        if (captureEnabled.compareAndSet(true, false)) {
            log.info("Capture service deactivated");
        }
    }

    @Async
    @Override
    public Resource captureToXlsxResource() throws IOException {
        if (!captureEnabled.get()) {
            throw new IllegalStateException("Capture service is disabled");
        }

        isRunning.set(true);
        try {
            File pcapFile = captureToPcap();
            File xlsxFile = convertToXlsx(pcapFile);
            return new FileSystemResource(xlsxFile);
        } finally {
            isRunning.set(false);
        }
    }

    private File captureToPcap() throws IOException {
        Path tempFile = Path.of(System.getProperty("java.io.tmpdir"), "capture_" + System.nanoTime() + ".pcap");

        // 将参数封装为数组
        String[] command = {
                "tshark",
                "-i", networkInterface,
                "-a", "duration:" + captureTimeout,
                "-w", tempFile.toString()
        };

        try {
            // 调用 executeWithTimeout
            ProcessUtils.executeWithTimeout(command, captureTimeout + 5);
        } catch (InterruptedException e) {
            // 恢复中断状态
            Thread.currentThread().interrupt();
            throw new IOException("Capture process was interrupted", e);
        }

        return tempFile.toFile();
    }



    private File convertToXlsx(File pcapFile) throws IOException {
        Path xlsxPath = Path.of(pcapFile.getAbsolutePath().replace(".pcap", ".xlsx"));

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(xlsxPath.toFile())) {

            Process process = ProcessUtils.startProcess(
                    "tshark",
                    "-r", pcapFile.getAbsolutePath(),
                    "-T", "fields",
                    "-E", "header=y",
                    "-e", "frame.number",
                    "-e", "frame.time",
                    "-e", "ip.src",
                    "-e", "ip.dst",
                    "-e", "tcp.srcport",
                    "-e", "tcp.dstport"
            );

            // 使用Apache POI填充Excel...
            workbook.write(out);
        }

        return xlsxPath.toFile();
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }
}

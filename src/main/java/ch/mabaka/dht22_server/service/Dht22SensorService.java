package ch.mabaka.dht22_server.service;

import ch.mabaka.dht22_server.domain.TemperatureHumidity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Service
public class Dht22SensorService {
    @Value("${dht22.script.path}")
    private String scriptPath;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TemperatureHumidity lastResult;

    private static final Logger logger = LoggerFactory.getLogger(Dht22SensorService.class);

    public Dht22SensorService() {
        // Initial read
        readSensor();
    }

    @Scheduled(fixedRate = 30000)
    public void readSensor() {
        try {
            final ProcessBuilder pb = new ProcessBuilder(scriptPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            final String output = new String(process.getInputStream().readAllBytes());
            final int exitCode = process.waitFor();
            if (exitCode == 0) {
                final TemperatureHumidity data = objectMapper.readValue(output, TemperatureHumidity.class);
                data.setReadTimestamp(Instant.now());
                synchronized (this) {
					lastResult = data;
				}
            }
        } catch (Exception e) {
            logger.warn("Exception while reading DHT22 sensor", e);
        }
    }

    public synchronized TemperatureHumidity getLastResult() {
        return lastResult;
    }
}
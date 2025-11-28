package ch.mabaka.dht22_server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.mabaka.dht22_server.domain.TemperatureHumidity;
import ch.mabaka.dht22_server.service.Dht22SensorService;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    @Value("${dht22.script.path}")
    private String scriptPath;


    @Autowired
    private Dht22SensorService sensorService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getSensorData() {
        TemperatureHumidity data = sensorService.getLastResult();
        if (data == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("No sensor data available yet.");
        }
        return ResponseEntity.ok(data);
    }
}
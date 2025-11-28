package ch.mabaka.dht22_server.domain;

import java.time.Instant;

public class TemperatureHumidity {
    private double temperature;
    private double humidity;
    private Instant readTimestamp;

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }
    public Instant getReadTimestamp() { return readTimestamp; }
    public void setReadTimestamp(Instant readTimestamp) { this.readTimestamp = readTimestamp; }
}
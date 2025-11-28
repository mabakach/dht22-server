#!/usr/bin/env python3
import Adafruit_DHT
import json
import sys
import time

SENSOR = Adafruit_DHT.DHT22
PIN = 4  # GPIO4 (physical pin D4)
MAX_ATTEMPTS = 3
DELAY_SECONDS = 2

for attempt in range(1, MAX_ATTEMPTS + 1):
    humidity, temperature = Adafruit_DHT.read_retry(SENSOR, PIN)
    if humidity is not None and temperature is not None:
        data = {
            "temperature": round(temperature, 1),
            "humidity": round(humidity, 1)
        }
        print(json.dumps(data))
        sys.exit(0)
    if attempt < MAX_ATTEMPTS:
        time.sleep(DELAY_SECONDS)

print(json.dumps({"error": f"Failed to read from DHT22 sensor after {MAX_ATTEMPTS} attempts"}))
sys.exit(1)
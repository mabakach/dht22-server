# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Spring Boot 4.2 / Java 21 REST server exposing DHT-22 temperature/humidity readings from a Raspberry Pi. Java does not touch GPIO: it shells out to a Python script.

## Commands

- Build + test: `mvn clean verify` (also builds dist zip `dht22-server-v<version>.zip` via maven-assembly-plugin)
- Run locally with simulated sensor: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- Single test: `mvn test -Dtest=Dht22ServerApplicationTests`
- Release: `./release.sh` (wraps `mvn release:prepare release:perform`; tag format = bare version)

## Architecture

- `Dht22SensorService`: `@Scheduled(fixedRate = 30000)` runs `${dht22.python.interpreter} ${dht22.script.path}` via `ProcessBuilder`. Script must print JSON `{"temperature":..,"humidity":..}` to stdout, exit 0. Parsed into `TemperatureHumidity`, timestamped, cached in memory (`lastResult`, synchronized). Failures only logged; last good value stays.
- `SensorController`: `GET /sensor` returns cached value, or 503 if no read succeeded yet. No on-demand sensor read.
- `bin/`: `dht22_reader.py` (real sensor, deployed), `dht22_sim.py` (random values, dev profile), `dht22-server.service` + `install-service.sh` (systemd install on Pi). Assembly `src/main/assembly/dist.xml` lists exactly which `bin/` files ship; update it when adding deployable files.
- Config: `application.properties` defaults to Pi path `/home/birdmin/dht22-server/dht22_reader.py` and is shipped in the dist zip; `application-dev.properties` points at `./bin/dht22_sim.py`. Logs go to `./logs` (`logback-spring.xml`).

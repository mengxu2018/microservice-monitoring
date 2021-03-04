package com.ss.demo;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Flux;

import java.time.Duration;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Autowired
	private HealthEndpoint healthEndpoint;

	@Bean
	MeterRegistryCustomizer prometheusHealthCheck() {
		return r -> r.gauge("health", healthEndpoint, e -> healthToCode(e));
	}

	private static int healthToCode(HealthEndpoint ep) {
		Status status = ep.health().getStatus();
		// The status will show DOWN if any of those health indicator components are ‘unhealthy’ for example a database is not reachable.
		return status.equals(Status.UP) ? 1 : 0;
	}



}

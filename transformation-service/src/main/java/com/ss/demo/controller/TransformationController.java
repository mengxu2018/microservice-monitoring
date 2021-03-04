package com.ss.demo.controller;

import com.ss.demo.bean.Employee;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xuhang
 */
@RestController
@RequestMapping(value = "transformation")
public class TransformationController {

	@Autowired
	private MeterRegistry meterRegistry;


	private Integer id = 0;

	private Counter transformationCounter;

	@PostConstruct
	public void initialize() {
		transformationCounter = Counter.builder("transformation")
				.description("The transformation count")
				.register(meterRegistry);


	}

	private int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	@GetMapping("/update")
	public String update() {
		int num = getRandomNumber(100, 1000);
		transformationCounter.increment(num);
		String s = "[Counter]transformation	 increased by " + num;
		return s;
	}




}
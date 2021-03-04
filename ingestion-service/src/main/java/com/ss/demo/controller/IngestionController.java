package com.ss.demo.controller;

import com.ss.demo.bean.Employee;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author xuhang
 */
@RestController
@RequestMapping(value = "ingestion")
public class IngestionController {

	@Autowired
	private MeterRegistry meterRegistry;

	private List sftpList = new ArrayList<>();;
	private List emailList = new ArrayList<>();;
	private List kafkaList = new ArrayList<>();;
	private List mqList = new ArrayList<>();;
	private List ndmList = new ArrayList<>();;


	private List ingestionList = new ArrayList<>();;

	@PostConstruct
	public void initialize() {
		System.out.println("IngestionController init");
		System.out.println(meterRegistry);

		Gauge.builder("sftp ingestion", sftpList, Collection::size)
				.description("sftp ingestion")
				.register(meterRegistry);

		Gauge.builder("email ingestion", emailList, Collection::size)
				.description("email ingestion")
				.register(meterRegistry);

		Gauge.builder("kafka ingestion", kafkaList, Collection::size)
				.description("kafka ingestion")
				.register(meterRegistry);

		Gauge.builder("mq ingestion", mqList, Collection::size)
				.description("mq ingestion")
				.register(meterRegistry);

		Gauge.builder("ndm ingestion", ndmList, Collection::size)
				.description("ndm ingestion")
				.register(meterRegistry);

		Gauge.builder("total ingestion", ingestionList, Collection::size)
				.description("total ingestion")
				.register(meterRegistry);


	}

	private int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	@GetMapping("/update")
	public String update() {

		sftpList.clear();
		int sftpNumber = getRandomNumber(0, 100);
		for (int i = 0; i < sftpNumber; i++) {
			sftpList.add(i);
		}

		emailList.clear();
		int emailNumber = getRandomNumber(0, 100);
		for (int i = 0; i < emailNumber; i++) {
			emailList.add(i);
		}

		kafkaList.clear();
		int kafkaNumber = getRandomNumber(0, 100);
		for (int i = 0; i < kafkaNumber; i++) {
			kafkaList.add(i);
		}

		mqList.clear();
		int mqNumber = getRandomNumber(0, 100);
		for (int i = 0; i < mqNumber; i++) {
			mqList.add(i);
		}

		ndmList.clear();
		int ndmNumber = getRandomNumber(0, 100);
		for (int i = 0; i < ndmNumber; i++) {
			ndmList.add(i);
		}

		ingestionList.clear();
		ingestionList.addAll(sftpList);
		ingestionList.addAll(emailList);
		ingestionList.addAll(mqList);
		ingestionList.addAll(kafkaList);
		ingestionList.addAll(ndmList);



		String s = "[Gauge]total ingestion update to " + ingestionList.size();
		return s;
	}




}
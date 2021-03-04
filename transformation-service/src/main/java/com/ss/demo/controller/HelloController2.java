package com.ss.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author xuhang
 */
@RestController
public class HelloController2 {

	@RequestMapping("/hello")
	public String index() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers.add(5);
		numbers.add(9);
		numbers.add(8);
		numbers.add(1);
		numbers.forEach( n ->  System.out.println(n) );
		return "hello Spring Boot!";
	}

}
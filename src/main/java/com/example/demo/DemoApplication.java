package com.example.demo;

import com.example.demo.myList.StringList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		StringList asd = new StringList();
		System.out.println(asd.size());
		asd.add(1);
		System.out.println(asd.size());
		asd.add(2);
		System.out.println(asd.size());
		asd.add(3);
		asd.add(4);
		asd.add(5);
		asd.add(6);
		System.out.println(asd.size());
	}

}

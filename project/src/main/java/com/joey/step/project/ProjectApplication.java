package com.joey.step.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import test.MyTest;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProjectApplication {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getClass().getClassLoader());
		SpringApplication.run(ProjectApplication.class, args);
	}
}

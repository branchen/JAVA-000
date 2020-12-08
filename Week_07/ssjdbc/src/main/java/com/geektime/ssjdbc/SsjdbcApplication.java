package com.geektime.ssjdbc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class})
public class SsjdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsjdbcApplication.class, args);
	}

}

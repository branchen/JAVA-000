package com.geektime.rwsepara01;

import com.geektime.rwsepara01.datasource.DynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({DynamicDataSource.class})
public class Rwsepara01Application {

	public static void main(String[] args) {
		SpringApplication.run(Rwsepara01Application.class, args);
	}

}

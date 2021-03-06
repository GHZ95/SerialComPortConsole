package com.zr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App extends SpringBootServletInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	
	 @Override//为了打包springboot项目
	    protected SpringApplicationBuilder configure(
	            SpringApplicationBuilder builder) {
	        return builder.sources(this.getClass());
	    }
}

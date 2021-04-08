package com.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Kalyan
 */
@SpringBootApplication
@PropertySource(ignoreResourceNotFound = true, value = { "classpath:config.properties" })
public class Bootstrap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(Bootstrap.class, args);
		/*
		 * Register JVM shutdown hook for the application context to
		 * gracefully close in case of JVM shutdown
		 */
		appContext.registerShutdownHook();
	}
}

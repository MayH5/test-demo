package com.asiainfo.qh.qhdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "com.asiainfo.qh.qhdataservice")
@ImportResource({"classpath:/spring/sptl-service-spring.xml"})
public class QhDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QhDataServiceApplication.class, args);
	}

}

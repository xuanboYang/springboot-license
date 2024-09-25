package com.modules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ClientApplication {

	public static void main(String[] args) {
		log.info("启动开始");
		SpringApplication.run(ClientApplication.class, args);
		log.info("启动成功");
	}
}

package com.jy.infonet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "com.jy.infonet.dao") //扫描所有的mapper加入ioc容器中
@SpringBootApplication
public class SoccerInfoMsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoccerInfoMsApplication.class, args);
	}
}

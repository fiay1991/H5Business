package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
		System.out.println("个数："+activeProfiles.length);
		for(String profile : activeProfiles){
			System.out.println("Spring Boot 使用profile为:{"+profile+"}" );
		}
	}
}

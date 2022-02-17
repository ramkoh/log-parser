package com.interview.cs.logParser;

import com.interview.cs.logParser.service.LogParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "command.line.runner", value = "enabled",
		havingValue = "true", matchIfMissing = true)
@SpringBootApplication
public class LogParser implements CommandLineRunner {

	@Autowired
	LogParserService logParserService;

	public static void main(String[] args) {
		SpringApplication.run(LogParser.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logParserService.validateAndParse(args);
	}
}

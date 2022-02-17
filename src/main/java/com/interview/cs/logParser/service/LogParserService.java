package com.interview.cs.logParser.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.interview.cs.logParser.exception.LogAnalyzerException;

@Service
public class LogParserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogParserService.class);

    @Autowired
    Parser parser;

    public void validateAndParse(String... args) {
        validate(args);
        parser.parseAndPersistLogEntries(args[0]);
    }

    private void validate(String... args) {
        validateParams(args);
        validateFilePath(args[0]);
    }

    private void validateFilePath(String path) {
        LOGGER.info("Processing logs from path: {}", path);
        try {
            File file = new ClassPathResource(path).getFile();
        } catch (IOException e) {
            throw new LogAnalyzerException(e.getMessage());
        }
    }

    private void validateParams(String[] args) {
        LOGGER.debug("Validating the arguments");
        if (args.length != 1) {
            throw new LogAnalyzerException("1 input arg expected for file path but " + args.length + " args provided.");
        }
    }
}

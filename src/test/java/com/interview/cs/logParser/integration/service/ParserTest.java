package com.interview.cs.logParser.integration.service;

import com.interview.cs.logParser.repository.AlertRepository;

import com.interview.cs.logParser.service.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = "logs.txt")
public class ParserTest {

    @Autowired
    Parser parser;

    @Autowired
    AlertRepository alertRepository;

    @Test
    public void testAlertsAreProcessed() {
        parser.parseAndPersistLogEntries("logs.txt");
        assert alertRepository.findAll().iterator().hasNext();
    }

}

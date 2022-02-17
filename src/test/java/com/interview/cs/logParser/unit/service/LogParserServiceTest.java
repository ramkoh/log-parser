package com.interview.cs.logParser.unit.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.interview.cs.logParser.exception.LogAnalyzerException;
import com.interview.cs.logParser.service.LogParserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"command.line.runner.enabled=false"})
public class LogParserServiceTest {

    @InjectMocks
    LogParserService logParserService;

    @Test
    public void testNoArgs() {
        LogAnalyzerException thrown = assertThrows(LogAnalyzerException.class, () -> logParserService.validateAndParse());
    }

    @Test
    public void testInvalidFilePath() {
        LogAnalyzerException thrown = assertThrows(
                LogAnalyzerException.class, () -> logParserService.validateAndParse("noFile.txt"),
                "1 input arg expected for file path but 0 args provided."
        );
    }
}

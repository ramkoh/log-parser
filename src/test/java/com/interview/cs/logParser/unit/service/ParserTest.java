package com.interview.cs.logParser.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.interview.cs.logParser.conf.ConfigData;
import com.interview.cs.logParser.repository.AlertRepository;
import com.interview.cs.logParser.service.Parser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"command.line.runner.enabled=false"})
public class ParserTest {

    @InjectMocks
    Parser parser;

    @Mock
    ConfigData configData;
    @Mock
    AlertRepository alertRepository;
    @Test
    public void testAlertsAreProcessed() {
        Mockito.when(configData.getBatchSize()).thenReturn(1);
        parser.parseAndPersistLogEntries("logs.txt");
        verify(alertRepository, times(3)).saveAll(any());

    }

    @Test
    public void testNoAlertGeneratedIfFinishLogIsMissing() {
        parser.parseAndPersistLogEntries("logs_without_finish.txt");
        verify(alertRepository, times(0)).saveAll(any());
    }

    @Test
    public void testNoAlertGeneratedForInvalidLog() {
        parser.parseAndPersistLogEntries("invalid_log_entry.txt");
        assert !alertRepository.findById("scsmbstgra").isPresent();
    }

}

package com.interview.cs.logParser.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.cs.logParser.conf.ConfigData;
import com.interview.cs.logParser.model.Alert;
import com.interview.cs.logParser.model.LogEntry;
import com.interview.cs.logParser.repository.AlertRepository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    @Autowired
    AlertRepository alertRepository;

    @Autowired
    ConfigData configData;

    public void parseAndPersistLogEntries(final String filePath) {
        Map<String, LogEntry> logEntries = new HashMap<>();
        Map<String, Alert> alerts = new HashMap<>();
        int noOfAlertsProcessed = 0;

        try (LineIterator iterator = FileUtils.lineIterator(new ClassPathResource(filePath).getFile())) {
            while (iterator.hasNext()) {
                final LogEntry logEntry = new ObjectMapper().readValue(iterator.nextLine(), LogEntry.class);
                LOGGER.trace("Working on eventId {} with state {}.", logEntry.getId(), logEntry.getState());
                if (logEntries.containsKey(logEntry.getId())) {
                    long executionTime = Math.abs(logEntries.get(logEntry.getId()).getTimestamp() - logEntry.getTimestamp());
                    alerts.put(logEntry.getId(), new Alert(logEntry, executionTime, configData.getThreshold()));
                    logEntries.remove(logEntry.getId());
                } else {
                    logEntries.put(logEntry.getId(), logEntry);
                }
                //save alerts in chunks to minimize memory requirements
                if (alerts.size() > configData.getBatchSize()) {
                    persistAlerts(alerts, noOfAlertsProcessed);
                }
            }
            if(!alerts.isEmpty()) {
                persistAlerts(alerts, noOfAlertsProcessed);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse the event {}", e.getMessage());
        }
        LOGGER.info("Finished processing alerts. Processed {} alerts.", noOfAlertsProcessed);
    }

    private void persistAlerts(Map<String, Alert> alerts, int noOfAlertsProcessed) {
        noOfAlertsProcessed += alerts.size();
        LOGGER.trace("Persisting {} alerts.", alerts.size());
        alertRepository.saveAll(alerts.values());
        alerts = new HashMap<>(); // re-initialize the map
    }

}

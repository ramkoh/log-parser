package com.interview.cs.logParser.repository;

import java.util.Optional;

import com.interview.cs.logParser.model.Alert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = "logs.txt")
public class AlertRepositoryTest {
    @Autowired
    private AlertRepository repository;

    @Test
    public void testAlertContentsAreCorrect() {
        Alert alert = new Alert();
        alert.setId("a1");
        alert.setDuration(10);
        alert.setHost("129.6.9.18");

        repository.save(alert);
        Optional<Alert> result = repository.findById("a1");
        assert result.isPresent();
        assert result.get().equals(alert);
    }

}
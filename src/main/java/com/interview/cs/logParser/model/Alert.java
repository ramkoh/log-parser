package com.interview.cs.logParser.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("duration")
    private long duration;

    @JsonProperty("type")
    private String type;

    @JsonProperty("host")
    private String host;

    @JsonProperty("alert")
    private Boolean alert;

    public Alert() {
    }

    public Alert(LogEntry logEntry, long duration, int threshold) {
        this.id = logEntry.getId();
        this.type = logEntry.getType();
        this.host = logEntry.getHost();
        this.duration = duration;
        this.alert = duration >= threshold ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert1 = (Alert) o;
        return (getDuration() == alert1.getDuration()) &&
                Objects.equals(getId(), alert1.getId()) &&
                getType() == alert1.getType() &&
                Objects.equals(getHost(), alert1.getHost()) &&
                Objects.equals(getAlert(), alert1.getAlert());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDuration(), getType(), getHost(), getAlert());
    }
}

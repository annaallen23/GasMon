package org.softwire.training.gasmon.model;

import com.google.common.base.MoreObjects;

public class Event {

    private String locationId;
    private String eventId;
    private double value;
    private long timestamp;

    public Event(String locationId, String eventId, double value, long timestamp) {
        this.locationId = locationId;
        this.eventId = eventId;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("locationId", locationId)
                .add("eventId", eventId)
                .add("value", value)
                .add("Timestamp", timestamp)
                .toString();

    }
}


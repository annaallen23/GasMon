package org.softwire.training.gasmon.services;

import org.softwire.training.gasmon.model.Event;
import org.softwire.training.gasmon.model.Location;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    public List<Event> validEvents = new ArrayList<>();

    public void addEvent(Event event) {
        validEvents.add(event);
    }

    public boolean hasPreviouslyBeenSeen(Event event) {
        for (Event validEvent : validEvents) {
            if (validEvent.getEventId().equals(event.getEventId())) {
                return true;
            }
        }
        return false;
    }

    public List<Event> timedEvents = new ArrayList<>();

    public void getTime(Event event) {
        timedEvents.add(event);
    }

    public double averageValueTime() {
        double sumOfValues = 0;
        int noOfEvents = 0;
        long currentTime = System.currentTimeMillis();
        for (Event timedEvent : timedEvents) {
            if ((currentTime-timedEvent.getTimestamp()) >= 300000 && (currentTime-timedEvent.getTimestamp()) < 360000){
                sumOfValues += timedEvent.getValue();
                noOfEvents++;
            }
        }
        return sumOfValues/noOfEvents;
    }
}



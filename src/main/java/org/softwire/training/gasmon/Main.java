package org.softwire.training.gasmon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softwire.training.gasmon.aws.AwsClientFactory;
import org.softwire.training.gasmon.config.Config;
import org.softwire.training.gasmon.model.Event;
import org.softwire.training.gasmon.model.Location;
import org.softwire.training.gasmon.receiver.QueueSubscription;
import org.softwire.training.gasmon.receiver.Receiver;
import org.softwire.training.gasmon.repository.S3Repository;
import org.softwire.training.gasmon.services.EventService;
import org.softwire.training.gasmon.services.LocationService;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            run();
        } catch (Throwable e) {
            LOG.error("Fatal error, terminating program", e);
            System.exit(1);
        }
    }

    private static void run() throws IOException {
        LOG.info("Starting to run...");

        Config config = new Config();

        AwsClientFactory awsClientFactory = new AwsClientFactory();
        AmazonSQS sqs = awsClientFactory.sqs();
        AmazonSNS sns = awsClientFactory.sns();
        AmazonS3 s3 = awsClientFactory.s3();

        S3Repository repository = new S3Repository(s3, config.locations.s3Bucket);
        LocationService locationService = new LocationService(repository, config.locations.s3Key);
        List<Location> locations = locationService.getValidLocations();

        for (Location location : locations) {
            LOG.info("{}", location);
        }

        EventService eventService = new EventService();

        try (QueueSubscription queueSubscription = new QueueSubscription(sqs, sns, config.receiver.snsTopicArn)) {
            Receiver receiver = new Receiver(sqs, queueSubscription.getQueueUrl());

            // when event id equals location id return event else ignore...
            // Your code here!
            // ...
            double timeLastAverageStamped = System.currentTimeMillis();
            long timeProgrammeStarted = System.currentTimeMillis();
            while (System.currentTimeMillis()< timeProgrammeStarted + 1800000) {
                List<Event> events = receiver.getEvents();
                for (Event event : events) {
                    if (locationService.isValidLocation(event.getLocationId())) {
                        if (!eventService.hasPreviouslyBeenSeen(event)) {
                            eventService.addEvent(event);
                            LOG.info("{}", event);
                            if (System.currentTimeMillis() - timeLastAverageStamped > 60000) {
                                LOG.info("the average value between {} & {} was {}", System.currentTimeMillis() - 360000, System.currentTimeMillis() - 300000, eventService.averageValueTime());
                                timeLastAverageStamped = System.currentTimeMillis();
                            } else {
                                LOG.info("event already been seen {}", event.getEventId());
                            }
                        } else {
                            LOG.info("skipped event with invalid location ID {}", event.getLocationId());
                        }
                    }
                }
            }

            for (Location location : locations) {
                LOG.info("the average for location {} is {}", location, eventService.averageValueAtLocation(location.getId()));
            }
        }
    }
}



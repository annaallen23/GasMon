package org.softwire.training.gasmon.services;

import com.google.gson.Gson;
import org.softwire.training.gasmon.model.Event;
import org.softwire.training.gasmon.model.Location;
import org.softwire.training.gasmon.repository.S3Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocationService {

    // method that returns list from JSON file

    private S3Repository s3Repository;
    private String locationsFileName;
    private final Gson gson = new Gson();

    public List<Location> validLocations;

    public LocationService(S3Repository s3Repository, String locationsFileName) {
        this.s3Repository = s3Repository;
        this.locationsFileName = locationsFileName;
    }

    public List<Location> getValidLocations() throws IOException {
        if(validLocations==null) {
            String locationJson = s3Repository.getObjectAtKey(locationsFileName);
            validLocations = Arrays.asList(gson.fromJson(locationJson, Location[].class));
        }
            return validLocations;
        }

    public boolean isValidLocation (String locationId) throws IOException{
        getValidLocations();
        for (Location location : validLocations) {
            if (location.getId().equals(locationId)){
                return true;
            }
        }
        return false;
    }

}


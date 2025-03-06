package com.example.geolocutil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: geoloc-util [--locations] \"Madison, WI\" \"12345\" ...");
            System.exit(1);
        }
        List<String> locations = new ArrayList<>();
        int startIndex = 0;
        if ("--locations".equalsIgnoreCase(args[0])) {
            startIndex = 1;
        }
        for (int i = startIndex; i < args.length; i++) {
            locations.add(args[i]);
        }

        GeoLocationService service = new GeoLocationService();

        for (String loc : locations) {
            try {
                GeoLocation geo = service.getLocation(loc);
                System.out.println("Input: " + loc);
                System.out.println("Name: " + geo.getName());
                System.out.println("Latitude: " + geo.getLat());
                System.out.println("Longitude: " + geo.getLon());
                if (geo.getState() != null) {
                    System.out.println("State: " + geo.getState());
                }
                System.out.println("Country: " + geo.getCountry());
                System.out.println();
            } catch (Exception e) {
                System.err.println("Error retrieving location for: " + loc + ". Error: " + e.getMessage());
            }
        }
    }
}

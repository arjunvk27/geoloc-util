package com.example.geolocutil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocation {
    private String name;
    private Double lat;
    private Double lon;
    private String state;
    private String country;

    // For responses from the zip endpoint
    private String zip;

    public GeoLocation() {}

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                (state != null ? ", state='" + state + '\'' : "") +
                ", country='" + country + '\'' +
                (zip != null ? ", zip='" + zip + '\'' : "") +
                '}';
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Dziňo
 */
public class BusStop {
    private String type;
    private String linkType;
    private double rideLength; 
    private double durationToStadion;
    private int travelersNumber;
    private int id;
    private int globalId;
    private int arrivedFans;
    
    public BusStop(String linkType, String type, double rideLength, int travelersNumber, int id){
        this.type = type;
        this.linkType = linkType;
        this. rideLength = rideLength;
        this.travelersNumber = travelersNumber;  
        this.id = id;
        this.arrivedFans = 0;
    }

    public String getType() {
        return type;
    }

    public double getRideLength() {
        return rideLength;
    }

    public int getTravelersNumber() {
        return travelersNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRideLength(double rideLength) {
        this.rideLength = rideLength;
    }

    public void setTravelersNumber(int travelersNumber) {
        this.travelersNumber = travelersNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDurationToStadion(double durationToStadion) {
        this.durationToStadion = durationToStadion;
    }

    public double getDurationToStadion() {
        return durationToStadion;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setglobalId(int generatorArriveId) {
        this.globalId = generatorArriveId;
    }

    public int getglobalId() {
        return globalId;
    }

    public int getArrivedFans() {
        return arrivedFans;
    }
    
    public void incArrivedFans() {
        this.arrivedFans++;
    }
}

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
public class Bus {
    private BusStop lastStop;
    
    private double startTime;
   
    private String myLink;
    private String status;
    
    private int busType;  // type 1 = 3 dvere, type 2 = 4 dvere
    private int idNextStop;
    private int idStartStop;
    private int id;
    private int globalId;
    private int maxSeats;
    private int seatsFull;
    private int doors;
    private int clodedDoor;
    private boolean isFull;
    private boolean isInBusStop;
    private boolean isWaitingInBusStopEnd;
    
    private double startTimeEmptyBus;
    private double fullTimeEmptyBus;
    
    private double profit;
    
    public Bus(int idStartStop, double startTime, String myLink, int busType) {
        this.idNextStop = idStartStop;
        this.idStartStop = idStartStop;
        this.startTime = /*(75 * 60) +*/startTime;
        this.myLink = myLink;
        this.isFull = false;
        this.seatsFull = 0;
        this.busType = busType;
        this.isInBusStop = false;
        this.status = "v depe";
        if(busType == 1) {
            this.maxSeats = 107;
            this.doors = 3;
        } else if (busType == 2) {
            this.maxSeats = 186;
            this.doors = 4;
        } else if (busType == 3) {
            this.maxSeats = 8;
            this.doors = 1;
        }
        
        this.clodedDoor = this.doors;
        this.startTimeEmptyBus = 0;
        this.fullTimeEmptyBus = 0;
        this.profit = 0;
        
        addDefaultBusStop();
    } 
    
    public BusStop getLastStop() {
       return lastStop;
    }

    public void setLastStop(BusStop lastStop) {
        this.lastStop = lastStop;
    }

    public int getId() {
        return id;
    }
    
    public void addDefaultBusStop(){
        BusStop defaultBS = new BusStop("D","Depo", 0, 0, 0);
        this.lastStop = defaultBS;
    }

    public int getIdNextStop() {
        return idNextStop;
    }

    public double getStartTime() {
        return startTime;
    }

    public String getMyLink() {
        return myLink;
    }

    public void setMyLink(String myLink) {
        this.myLink = myLink;
    }

    public void setIdNextStop(int idNextStop) {
        this.idNextStop = idNextStop;
    }
    
    public void incIdNextStop() {
        this.idNextStop++;
    }

    public int getBusType() {
        return busType;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isIsFull() {
        return isFull;
    }
    
    public void incSeatsFul() {
        this.seatsFull++;
    }

    public void descSeatsFull() {
        this.seatsFull--;
    }
    
    public int getSeatsFull() {
        return seatsFull;
    }

    public void setSeatsFull(int seatsFull) {
        this.seatsFull = seatsFull;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getDoors() {
        return doors;
    }

    public int getClodedDoor() {
        return clodedDoor;
    }

    public void setClodedDoor(int clodedDoor) {
        this.clodedDoor = clodedDoor;
    }
    public void incClosedDoor() {
        this.clodedDoor++;
    }

    public void descClosedDoor() {
        this.clodedDoor--;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsInBusStop() {
        return isInBusStop;
    }

    public void setIsInBusStop(boolean isInBusStop) {
        this.isInBusStop = isInBusStop;
    }

    public boolean isIsWaitingInBusStopEnd() {
        return isWaitingInBusStopEnd;
    }

    public void setIsWaitingInBusStopEnd(boolean isWaitingInBusStopEnd) {
        this.isWaitingInBusStopEnd = isWaitingInBusStopEnd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getGlobalId() {
        return globalId;
    }

    public void setGlobalId(int globalId) {
        this.globalId = globalId;
    }

    public double getStartTimeEmptyBus() {
        return startTimeEmptyBus;
    }

    public double getFullTimeEmptyBus() {
        return fullTimeEmptyBus;
    }

    public void setStartTimeEmptyBus(double startTimeEmptyBus) {
        this.startTimeEmptyBus = startTimeEmptyBus;
    }

    public void setFullTimeEmptyBus(double fullTimeEmptyBus) {
        this.fullTimeEmptyBus += fullTimeEmptyBus;
    }

    public int getIdStartStop() {
        return idStartStop;
    }

    public double getProfit() {
        return profit;
    }
    
    public void incProfit() {
        this.profit++;
    }
}

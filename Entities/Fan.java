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
public class Fan {
    private int id;
    private double startTimeInSystem;
    private double fullTimeInSystem;
    
        public Fan(int id){
            this.id = id;
        }

    public double getStartTimeInSystem() {
        return startTimeInSystem;
    }

    public double getFullTimeInSystem() {
        return fullTimeInSystem;
    }

    public void setStartTimeInSystem(double startTimeInSystem) {
        this.startTimeInSystem = startTimeInSystem;
    }

    public void setFullTimeInSystem(double fullTimeInSystem) {
        this.fullTimeInSystem = fullTimeInSystem;
    }
        
    
}

package agents;

import Entities.Bus;
import Entities.BusStop;
import Entities.Fan;
import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
//import instantAssistants.*;

//meta! id="3"
public class BusAgent extends Agent
{
        private ArrayList<String> dataForBusesLinkaA3;
        private ArrayList<String> dataForBusesLinkaA4;
        private ArrayList<String> dataForBusesLinkaA1;
        private ArrayList<String> dataForBusesLinkaB3;
        private ArrayList<String> dataForBusesLinkaB4;
        private ArrayList<String> dataForBusesLinkaB1;
        private ArrayList<String> dataForBusesLinkaC3;
        private ArrayList<String> dataForBusesLinkaC4;
        private ArrayList<String> dataForBusesLinkaC1;
    
        private ArrayList<Bus> buses_A;    
        private ArrayList<Bus> buses_B;  
        private ArrayList<Bus> buses_C;  
        
        private ArrayList<Bus> allBuses;  
    
        private ArrayList<ArrayList<Fan>> fansInBusAllBuses;
        
        int numberOfPlanedBusesLinkA;
        int numberOfPlanedBusesLinkB;
        int numberOfPlanedBusesLinkC;
        
        private Stat emptyBusesLinkATime;
        private Stat emptyBusesLinkBTime;
        private Stat emptyBusesLinkCTime;
        
        private double profitForMicrobuses;
        
	public BusAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
                

                
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
                buses_A = new ArrayList<Bus>();
                buses_B = new ArrayList<Bus>();
                buses_C = new ArrayList<Bus>();
                
                allBuses = new ArrayList<Bus>();
                      
                fansInBusAllBuses = new ArrayList<ArrayList<Fan>>();
                
                numberOfPlanedBusesLinkA = 0;
                numberOfPlanedBusesLinkB = 0;
                numberOfPlanedBusesLinkC = 0;
                                 
                        
                this.addBusesLinkA(dataForBusesLinkaA3, 1);
                this.addBusesLinkA(dataForBusesLinkaA4, 2);
                this.addBusesLinkA(dataForBusesLinkaA1, 3);
                this.addBusesLinkB(dataForBusesLinkaB3, 1);
                this.addBusesLinkB(dataForBusesLinkaB4, 2);
                this.addBusesLinkB(dataForBusesLinkaB1, 3);
                this.addBusesLinkC(dataForBusesLinkaC3, 1);
                this.addBusesLinkC(dataForBusesLinkaC4, 2);
                this.addBusesLinkC(dataForBusesLinkaC1, 3);
                this.setIndexesForBuses();
                
                emptyBusesLinkATime = new Stat();
                emptyBusesLinkBTime = new Stat();
                emptyBusesLinkCTime = new Stat();
                
                profitForMicrobuses = 0;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new BusManager(Id.busManager, mySim(), this);
		new Riding(Id.riding, mySim(), this);
		new RidingPlanning(Id.ridingPlanning, mySim(), this);
		new BusStopServices(Id.busStopServices, mySim(), this);
		addOwnMessage(Mc.notice);
		addOwnMessage(Mc.requestResponse);
		addOwnMessage(Mc.requestResponseGetCurrBusStop);
                
                addOwnMessage(Mc.allCustomersLeave);
                addOwnMessage(Mc.init);
                addOwnMessage(Mc.planningBusEnd);
                addOwnMessage(Mc.ridingBus);
                addOwnMessage(Mc.ridingBusEnd);
                addOwnMessage(Mc.busStopService);
                addOwnMessage(Mc.busStopServiceEnd);
                addOwnMessage(Mc.startBusPLanning);
                addOwnMessage(Mc.customerBoarded);
	}
	//meta! tag="end"
    
        public void addBusesLinkA(ArrayList<String> array, int busType) {
        for(int i = 0; i < array.size(); i++){
            String busStopId = array.get(i).substring(0,2);
            int busStopIdI = Integer.parseInt(busStopId);
            String startTime = array.get(i).substring(3,array.get(i).length());
            int startTimeI = Integer.parseInt(startTime);
            Bus newBus = new Bus(busStopIdI, startTimeI, "A", busType);
            buses_A.add(newBus);
            
            newBus.setGlobalId(allBuses.size());
            allBuses.add(newBus);
            
            ArrayList<Fan> fansInBus = new ArrayList<Fan>();
            fansInBusAllBuses.add(fansInBus);
        }
    }
    
        public void addBusesLinkB(ArrayList<String> array, int busType) {
        for(int i = 0; i < array.size(); i++){
            String busStopId = array.get(i).substring(0,2);
            int busStopIdI = Integer.parseInt(busStopId);
            String startTime = array.get(i).substring(3,array.get(i).length());
            int startTimeI = Integer.parseInt(startTime);
            Bus newBus = new Bus(busStopIdI, startTimeI, "B", busType);
            buses_B.add(newBus);
            newBus.setGlobalId(allBuses.size());
            allBuses.add(newBus);
            
            ArrayList<Fan> fansInBus = new ArrayList<Fan>();
            fansInBusAllBuses.add(fansInBus);
        }
    }
        
        public void addBusesLinkC(ArrayList<String> array, int busType) {
        for(int i = 0; i < array.size(); i++){
            String busStopId = array.get(i).substring(0,2);
            int busStopIdI = Integer.parseInt(busStopId);
            String startTime = array.get(i).substring(3,array.get(i).length());
            int startTimeI = Integer.parseInt(startTime);
            Bus newBus = new Bus(busStopIdI, startTimeI, "C", busType);
            buses_C.add(newBus);
            newBus.setGlobalId(allBuses.size());
            allBuses.add(newBus);
            
            ArrayList<Fan> fansInBus = new ArrayList<Fan>();
            fansInBusAllBuses.add(fansInBus);
        }
    }    

        
        public void setIndexesForBuses() {
            for(int  i = 0; i <  buses_A.size(); i++) {
                buses_A.get(i).setId(i);
            }
            
            for(int  i = 0; i <  buses_B.size(); i++) {
                buses_B.get(i).setId(i);
            }
            
            for(int  i = 0; i <  buses_C.size(); i++) {
                buses_C.get(i).setId(i);
            }
        }

        
    public int getNumberOfPlanedBusesLinkA() {
        return numberOfPlanedBusesLinkA;
    }

    public int getNumberOfPlanedBusesLinkB() {
        return numberOfPlanedBusesLinkB;
    }

    public int getNumberOfPlanedBusesLinkC() {
        return numberOfPlanedBusesLinkC;
    }
        
    public void incNumberOfPlanedBusesLinkA() {
        numberOfPlanedBusesLinkA++;
    }   
    
    public void incNumberOfPlanedBusesLinkB() {
        numberOfPlanedBusesLinkB++;
    }  
    
    public void incNumberOfPlanedBusesLinkC() {
        numberOfPlanedBusesLinkC++;
    }   

    public ArrayList<Bus> getBuses_A() {
        return buses_A;
    }

    public ArrayList<Bus> getBuses_B() {
        return buses_B;
    }

    public ArrayList<Bus> getBuses_C() {
        return buses_C;
    }

    public ArrayList<Bus> getAllBuses() {
        return allBuses;
    }

    public ArrayList<ArrayList<Fan>> getFansInBusAllBuses() {
        return fansInBusAllBuses;
    }
    
    
    public void setDataForBusesLinkaA3(ArrayList<String> dataForBusesLinkaA3) {
        this.dataForBusesLinkaA3 = dataForBusesLinkaA3;
    }

    public void setDataForBusesLinkaA4(ArrayList<String> dataForBusesLinkaA4) {
        this.dataForBusesLinkaA4 = dataForBusesLinkaA4;
    }

    public void setDataForBusesLinkaA1(ArrayList<String> dataForBusesLinkaA1) {
        this.dataForBusesLinkaA1 = dataForBusesLinkaA1;
    }

    public void setDataForBusesLinkaB3(ArrayList<String> dataForBusesLinkaB3) {
        this.dataForBusesLinkaB3 = dataForBusesLinkaB3;
    }

    public void setDataForBusesLinkaB4(ArrayList<String> dataForBusesLinkaB4) {
        this.dataForBusesLinkaB4 = dataForBusesLinkaB4;
    }

    public void setDataForBusesLinkaB1(ArrayList<String> dataForBusesLinkaB1) {
        this.dataForBusesLinkaB1 = dataForBusesLinkaB1;
    }

    public void setDataForBusesLinkaC3(ArrayList<String> dataForBusesLinkaC3) {
        this.dataForBusesLinkaC3 = dataForBusesLinkaC3;
    }

    public void setDataForBusesLinkaC4(ArrayList<String> dataForBusesLinkaC4) {
        this.dataForBusesLinkaC4 = dataForBusesLinkaC4;
    }

    public void setDataForBusesLinkaC1(ArrayList<String> dataForBusesLinkaC1) {
        this.dataForBusesLinkaC1 = dataForBusesLinkaC1;
    }

    public Stat getEmptyBusesLinkATime() {
        return emptyBusesLinkATime;
    }

    public Stat getEmptyBusesLinkBTime() {
        return emptyBusesLinkBTime;
    }

    public Stat getEmptyBusesLinkCTime() {
        return emptyBusesLinkCTime;
    }

    public double getProfitForMicrobuses() {
        return profitForMicrobuses;
    }
    
    
    
    public void calculateProfitMicrobuses() {
        this.profitForMicrobuses = 0;
        for(int i = 0; i < buses_A.size(); i++) {
            Bus myBus = buses_A.get(i);
            if(myBus.getBusType() == 3){
                this.profitForMicrobuses += myBus.getProfit();
            }
        }
        
        for(int i = 0; i < buses_B.size(); i++) {
            Bus myBus = buses_B.get(i);
            if(myBus.getBusType() == 3){
                this.profitForMicrobuses += myBus.getProfit();
            }
        }
        
        for(int i = 0; i < buses_C.size(); i++) {
            Bus myBus = buses_C.get(i);
            if(myBus.getBusType() == 3){
                this.profitForMicrobuses += myBus.getProfit();
            }
        }
    }
}
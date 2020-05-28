package agents;

import Entities.BusStop;
import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import OSPStat.Stat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
//import instantAssistants.*;

//meta! id="4"
public class BusStopAgent extends Agent
{
    private Stat waitingTimeStat;
    
    private Stat waitingTimeStatLinkA;
    private Stat waitingTimeStatLinkB;
    private Stat waitingTimeStatLinkC;
    
    private double waitingTimeLinkASum;
    private double waitingTimeLinkBSum;
    private double waitingTimeLinkCSum;
    
    private ArrayList<BusStop> busStops_A;
    private ArrayList<BusStop> busStops_B;
    private ArrayList<BusStop> busStops_C;
    private ArrayList<BusStop> allBusStops;
    
    private ArrayList<SimQueue< MessageForm >> fansAllLinks;
    
    private ArrayList<SimQueue< MessageForm >> busesOnBusStops;
    
    private double drivingDurationLinkA;
    private double drivingDurationLinkB;
    private double drivingDurationLinkC;
    
    private TriangularRNG boardingGen;
    private UniformContinuousRNG boardingGenMicroBus;
    
    BusStop K1;
    BusStop K2;
    BusStop K3;
    
    SimQueue< MessageForm > linkWaitingFans_K1;
    SimQueue< MessageForm > linkWaitingFans_K2;
    SimQueue< MessageForm > linkWaitingFans_K3;
	public BusStopAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();               
                
                boardingGen = new TriangularRNG(0.6, 1.2, 4.2);
                boardingGenMicroBus = new UniformContinuousRNG(6.0, 10.0);
        
	}

	@Override
	public void prepareReplication()
	{            
		super.prepareReplication();
		// Setup component for the next replication
            waitingTimeStat = new Stat();
            
            waitingTimeStatLinkA = new Stat();
            waitingTimeStatLinkB = new Stat();
            waitingTimeStatLinkC = new Stat();
            
            waitingTimeLinkASum = 0;
            waitingTimeLinkBSum = 0;
            waitingTimeLinkCSum = 0;
            
            busStops_A = new ArrayList<BusStop>();
            
            allBusStops = new ArrayList<BusStop>();
                
            fansAllLinks = new ArrayList<SimQueue< MessageForm >>();
                
            busesOnBusStops = new ArrayList<SimQueue< MessageForm >>();
            
            K1 = new BusStop("K","K1", 5.4 * 60, 260, 4);
            K2 = new BusStop("K","K2", 1.2 * 60, 210, 4);
            K3 = new BusStop("K","K3", 4.0 * 60, 220, 8);
            
            linkWaitingFans_K1 = new SimQueue< MessageForm >();
            linkWaitingFans_K2 = new SimQueue< MessageForm >();
            linkWaitingFans_K3 = new SimQueue< MessageForm >();
            
            drivingDurationLinkA = 0;
            drivingDurationLinkB = 0;
            drivingDurationLinkC = 0;
            
            this.addAllBussStops_LinkA();
            this.addAllBussStops_LinkB();
            this.addAllBussStops_LinkC();
            this.calcDrivingDurationLinks();
            this.addBusesOnBusStopsFronts();
            
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new BusStopManager(Id.busStopManager, mySim(), this);
		new BusWaitingOnBusStop(Id.busWaitingOnBusStop, mySim(), this);
		new Boarding(Id.boarding, mySim(), this);
		addOwnMessage(Mc.notice);
		addOwnMessage(Mc.requestResponse);
		addOwnMessage(Mc.requestResponseGetBusStops);
		addOwnMessage(Mc.requestResponseGetCurrBusStop);
                
                addOwnMessage(Mc.busStopServiceEnd);
                addOwnMessage(Mc.customerArrived);
                addOwnMessage(Mc.busStopService);
                addOwnMessage(Mc.customerBoarded);
                addOwnMessage(Mc.busWaitingOnBusStopEnd);
	}
	//meta! tag="end"

        public void addAllBussStops_LinkA(){
            
            BusStop AA = new BusStop("A","AA", 3.2 * 60, 123, 0);
            SimQueue< MessageForm > linkAWaitingFans_AA = new SimQueue< MessageForm >(); 
            BusStop AB = new BusStop("A","AB", 2.3 * 60, 92, 1);
            SimQueue< MessageForm > linkAWaitingFans_AB = new SimQueue< MessageForm >(); 
            BusStop AC = new BusStop("A","AC", 2.1 * 60, 241, 2);
            SimQueue< MessageForm > linkAWaitingFans_AC = new SimQueue< MessageForm >(); 
            BusStop AD = new BusStop("A","AD", 1.2 * 60, 123, 3);
            SimQueue< MessageForm > linkAWaitingFans_AD = new SimQueue< MessageForm >();  
            BusStop AE = new BusStop("A","AE", 2.9 * 60, 215, 5);
            SimQueue< MessageForm > linkAWaitingFans_AE = new SimQueue< MessageForm >(); 
            BusStop AF = new BusStop("A","AF", 3.4 * 60, 245, 6);
            SimQueue< MessageForm > linkAWaitingFans_AF = new SimQueue< MessageForm >(); 
            BusStop AG = new BusStop("A","AG", 1.8 * 60, 137, 7);
            SimQueue< MessageForm > linkAWaitingFans_AG = new SimQueue< MessageForm >();        
            BusStop AH = new BusStop("A","AH", 1.6 * 60, 132, 9);
            SimQueue< MessageForm > linkAWaitingFans_AH = new SimQueue< MessageForm >(); 
            BusStop AI = new BusStop("A","AI", 4.6 * 60, 164, 10);
            SimQueue< MessageForm > linkAWaitingFans_AI = new SimQueue< MessageForm >(); 
            BusStop AJ = new BusStop("A","AJ", 3.4 * 60, 124, 11);
            SimQueue< MessageForm > linkAWaitingFans_AJ = new SimQueue< MessageForm >(); 
            BusStop AK = new BusStop("A","AK", 1.2 * 60, 213, 12);
            SimQueue< MessageForm > linkAWaitingFans_AK = new SimQueue< MessageForm >(); 
            BusStop AL = new BusStop("A","AL", 0.9 * 60, 185, 13);
            SimQueue< MessageForm > linkAWaitingFans_AL = new SimQueue< MessageForm >();    
            BusStop Sta = new BusStop("A","ST", 1500, 0, 14);
            
            fansAllLinks.add(linkAWaitingFans_AA);
            fansAllLinks.add(linkAWaitingFans_AB);
            fansAllLinks.add(linkAWaitingFans_AC);
            fansAllLinks.add(linkAWaitingFans_AD);
            fansAllLinks.add(linkWaitingFans_K1);
            fansAllLinks.add(linkAWaitingFans_AE);
            fansAllLinks.add(linkAWaitingFans_AF);
            fansAllLinks.add(linkAWaitingFans_AG);
            fansAllLinks.add(linkWaitingFans_K3);
            fansAllLinks.add(linkAWaitingFans_AH);
            fansAllLinks.add(linkAWaitingFans_AI);
            fansAllLinks.add(linkAWaitingFans_AJ);
            fansAllLinks.add(linkAWaitingFans_AK);
            fansAllLinks.add(linkAWaitingFans_AL);
            
            busStops_A.add(AA);
            busStops_A.add(AB);
            busStops_A.add(AC);
            busStops_A.add(AD);
            busStops_A.add(K1);
            busStops_A.add(AE);
            busStops_A.add(AF);
            busStops_A.add(AG);
            busStops_A.add(K3);
            busStops_A.add(AH);
            busStops_A.add(AI);
            busStops_A.add(AJ);
            busStops_A.add(AK);
            busStops_A.add(AL);
            busStops_A.add(Sta);
            
            allBusStops.add(AA);
            allBusStops.add(AB);
            allBusStops.add(AC);
            allBusStops.add(AD);
            allBusStops.add(K1);
            allBusStops.add(AE);
            allBusStops.add(AF);
            allBusStops.add(AG);
            allBusStops.add(K3);
            allBusStops.add(AH);
            allBusStops.add(AI);
            allBusStops.add(AJ);
            allBusStops.add(AK);
            allBusStops.add(AL);
            
            for(int i = 0; i < busStops_A.size() - 1; i++) {
                double max  = busStops_A.get(i).getDurationToStadion();               
                double fromHereToStadion = 0;
                for(int j = i; j < busStops_A.size() - 1; j++) {
                    fromHereToStadion += busStops_A.get(j).getRideLength();
                }
                if(busStops_A.get(i).getType() == "K"){
                    if(max < fromHereToStadion){
                        busStops_A.get(i).setDurationToStadion(fromHereToStadion);
                    }
                } else {
                    busStops_A.get(i).setDurationToStadion(fromHereToStadion);
                }
            }
        }
        
        public void addAllBussStops_LinkB(){
            busStops_B = new ArrayList<BusStop>();
            
            BusStop BA = new BusStop("B","BA", 1.2 * 60, 79, 0);
            SimQueue< MessageForm > linkAWaitingFans_BA = new SimQueue< MessageForm >();
            BusStop BB = new BusStop("B","BB", 2.3 * 60, 69, 1);
            SimQueue< MessageForm > linkAWaitingFans_BB = new SimQueue< MessageForm >();
            BusStop BC = new BusStop("B","BC", 3.2 * 60, 43, 2);
            SimQueue< MessageForm > linkAWaitingFans_BC = new SimQueue< MessageForm >();
            BusStop BD = new BusStop("B","BD", 4.3 * 60, 127, 3);
            SimQueue< MessageForm > linkAWaitingFans_BD = new SimQueue< MessageForm >();
            BusStop BE = new BusStop("B","BE", 2.7 * 60, 30, 5);
            SimQueue< MessageForm > linkAWaitingFans_BE = new SimQueue< MessageForm >();
            BusStop BF = new BusStop("B","BF", 3.0 * 60, 69, 6);
            SimQueue< MessageForm > linkAWaitingFans_BF = new SimQueue< MessageForm >();
            BusStop BG = new BusStop("B","BG", 4.3 * 60, 162, 8);
            SimQueue< MessageForm > linkAWaitingFans_BG = new SimQueue< MessageForm >();
            BusStop BH = new BusStop("B","BH", 0.5 * 60, 90, 9);
            SimQueue< MessageForm > linkAWaitingFans_BH = new SimQueue< MessageForm >();
            BusStop BI = new BusStop("B","BI", 2.7 * 60, 148, 10);
            SimQueue< MessageForm > linkAWaitingFans_BI = new SimQueue< MessageForm >();
            BusStop BJ = new BusStop("B","BJ", 1.3 * 60, 171, 11);
            SimQueue< MessageForm > linkAWaitingFans_BJ = new SimQueue< MessageForm >();
            BusStop Sta = new BusStop("B","ST", 600, 0, 12);
            
            fansAllLinks.add(linkAWaitingFans_BA);
            fansAllLinks.add(linkAWaitingFans_BB);
            fansAllLinks.add(linkAWaitingFans_BC);
            fansAllLinks.add(linkAWaitingFans_BD);
            fansAllLinks.add(linkWaitingFans_K2);
            fansAllLinks.add(linkAWaitingFans_BE);
            fansAllLinks.add(linkAWaitingFans_BF);
            fansAllLinks.add(linkAWaitingFans_BG);
            fansAllLinks.add(linkAWaitingFans_BH);
            fansAllLinks.add(linkAWaitingFans_BI);
            fansAllLinks.add(linkAWaitingFans_BJ);
                        
            busStops_B.add(BA);
            busStops_B.add(BB);
            busStops_B.add(BC);
            busStops_B.add(BD);
            busStops_B.add(K2);
            busStops_B.add(BE);
            busStops_B.add(BF);
            busStops_B.add(K3);
            busStops_B.add(BG);
            busStops_B.add(BH);
            busStops_B.add(BI);
            busStops_B.add(BJ);
            busStops_B.add(Sta);
            
            allBusStops.add(BA);
            allBusStops.add(BB);
            allBusStops.add(BC);
            allBusStops.add(BD);
            allBusStops.add(K2);
            allBusStops.add(BE);
            allBusStops.add(BF);
            allBusStops.add(BG);
            allBusStops.add(BH);
            allBusStops.add(BI);
            allBusStops.add(BJ);
            
            for(int i = 0; i < busStops_B.size() - 1; i++) {
                double max  = busStops_B.get(i).getDurationToStadion();               
                double fromHereToStadion = 0;
                for(int j = i; j < busStops_B.size() - 1; j++) {
                    fromHereToStadion += busStops_B.get(j).getRideLength();
                }
                if(busStops_B.get(i).getType() == "K"){
                    if(max < fromHereToStadion){
                        busStops_B.get(i).setDurationToStadion(fromHereToStadion);
                    }
                } else {
                    busStops_B.get(i).setDurationToStadion(fromHereToStadion);
                }
            }
        }
        
        public void addAllBussStops_LinkC(){
            busStops_C = new ArrayList<BusStop>();
            
            BusStop CA = new BusStop("C","CA", 0.6 * 60, 240, 0);
            SimQueue< MessageForm > linkAWaitingFans_CA = new SimQueue< MessageForm >();
            BusStop CB = new BusStop("C","CB", 2.3 * 60, 310, 1);
            SimQueue< MessageForm > linkAWaitingFans_CB = new SimQueue< MessageForm >();
            BusStop CC = new BusStop("C","CC", 2.3 * 60, 131, 4);
            SimQueue< MessageForm > linkAWaitingFans_CC = new SimQueue< MessageForm >();
            BusStop CD = new BusStop("C","CD", 7.1 * 60, 190, 5);
            SimQueue< MessageForm > linkAWaitingFans_CD = new SimQueue< MessageForm >();
            BusStop CE = new BusStop("C","CE", 4.8 * 60, 132, 6);
            SimQueue< MessageForm > linkAWaitingFans_CE = new SimQueue< MessageForm >();
            BusStop CF = new BusStop("C","CF", 3.7 * 60, 128, 7);
            SimQueue< MessageForm > linkAWaitingFans_CF = new SimQueue< MessageForm >();
            BusStop CG = new BusStop("C","CG", 7.2 * 60, 70, 8);
            SimQueue< MessageForm > linkAWaitingFans_CG = new SimQueue< MessageForm >();
            BusStop Sta = new BusStop("C","ST", 1800, 0, 9);
            
            fansAllLinks.add(linkAWaitingFans_CA);
            fansAllLinks.add(linkAWaitingFans_CB);
            fansAllLinks.add(linkAWaitingFans_CC);
            fansAllLinks.add(linkAWaitingFans_CD);
            fansAllLinks.add(linkAWaitingFans_CE);
            fansAllLinks.add(linkAWaitingFans_CF);
            fansAllLinks.add(linkAWaitingFans_CG);            
                        
            busStops_C.add(CA);
            busStops_C.add(CB);
            busStops_C.add(K1);
            busStops_C.add(K2);
            busStops_C.add(CC);
            busStops_C.add(CD);
            busStops_C.add(CE);
            busStops_C.add(CF);
            busStops_C.add(CG);
            busStops_C.add(Sta);
            
            allBusStops.add(CA);
            allBusStops.add(CB);
            allBusStops.add(CC);
            allBusStops.add(CD);
            allBusStops.add(CE);
            allBusStops.add(CF);
            allBusStops.add(CG);
            
            for(int i = 0; i < busStops_C.size() - 1; i++) {
                double max  = busStops_C.get(i).getDurationToStadion();               
                double fromHereToStadion = 0;
                for(int j = i; j < busStops_C.size() - 1; j++) {
                    fromHereToStadion += busStops_C.get(j).getRideLength();
                }
                if(busStops_C.get(i).getType() == "K"){
                    if(max < fromHereToStadion){
                        busStops_C.get(i).setDurationToStadion(fromHereToStadion);
                    }
                } else {
                    busStops_C.get(i).setDurationToStadion(fromHereToStadion);
                }
            }
        }
        
        public void calcDrivingDurationLinks(){
            for(int i = 0; i < busStops_A.size() - 1; i++) { //okrem Stadionu
                this.drivingDurationLinkA += busStops_A.get(i).getRideLength();
            }
            
            for(int i = 0; i < busStops_B.size() - 1; i++) {
                this.drivingDurationLinkB += busStops_B.get(i).getRideLength();
            }
            
            for(int i = 0; i < busStops_C.size() - 1; i++) {
                this.drivingDurationLinkC += busStops_C.get(i).getRideLength();
            }
        }

    public UniformContinuousRNG getBoardingGenMicroBus() {
        return boardingGenMicroBus;
    }
   
    public double getDrivingDurationLinkA() {
        return drivingDurationLinkA;
    }

    public double getDrivingDurationLinkB() {
        return drivingDurationLinkB;
    }

    public double getDrivingDurationLinkC() {
        return drivingDurationLinkC;
    }

    public ArrayList<BusStop> getBusStops_A() {
        return busStops_A;
    }

    public ArrayList<BusStop> getBusStops_B() {
        return busStops_B;
    }

    public ArrayList<BusStop> getBusStops_C() {
        return busStops_C;
    }

    public ArrayList<SimQueue<MessageForm>> getFansAllLinks() {
        return fansAllLinks;
    }

    public double getMaxSumDuration() {
        
        double max1 = Math.max(drivingDurationLinkA, drivingDurationLinkB);
        double max = Math.max(max1,drivingDurationLinkC);

        return max;
    }

    public Stat getWaitingTimeStat() {
        return waitingTimeStat;
    }

    public TriangularRNG getBoardingGen() {
        return boardingGen;
    }

    public ArrayList<BusStop> getAllBusStops() {
        return allBusStops;
    }
    
    public MyMessage getFirstFanToMicroBus(SimQueue< MessageForm > fans, double currentTime) {
        for(int i = 0; i < fans.size(); i++){
            double startWaiting = ((MyMessage)fans.get(i)).getFanStartWaiting();
            if((currentTime - startWaiting) > 360){
                MyMessage fan = (MyMessage)fans.get(i).createCopy();
                fans.remove(i);
                return fan;
            }
        }
        return null;
    }
    
    public void addBusesOnBusStopsFronts() {
        for(int i = 0; i < allBusStops.size(); i++ ) {
            SimQueue< MessageForm > buses = new SimQueue< MessageForm >();
            busesOnBusStops.add(buses);
        }
    }

    public ArrayList<SimQueue<MessageForm>> getBusesOnBusStops() {
        return busesOnBusStops;
    }

    public Stat getWaitingTimeStatLinkA() {
        return waitingTimeStatLinkA;
    }

    public Stat getWaitingTimeStatLinkB() {
        return waitingTimeStatLinkB;
    }

    public Stat getWaitingTimeStatLinkC() {
        return waitingTimeStatLinkC;
    }

    public double getWaitingTimeLinkASum() {
        return waitingTimeLinkASum;
    }

    public double getWaitingTimeLinkBSum() {
        return waitingTimeLinkBSum;
    }

    public double getWaitingTimeLinkCSum() {
        return waitingTimeLinkCSum;
    }

    public void setWaitingTimeLinkASum(double waitingTimeLinkASum) {
        this.waitingTimeLinkASum += waitingTimeLinkASum;
    }

    public void setWaitingTimeLinkBSum(double waitingTimeLinkBSum) {
        this.waitingTimeLinkBSum += waitingTimeLinkBSum;
    }

    public void setWaitingTimeLinkCSum(double waitingTimeLinkCSum) {
        this.waitingTimeLinkCSum += waitingTimeLinkCSum;
    }

    
}
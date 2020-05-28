package agents;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import OSPStat.Stat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
//import instantAssistants.*;

//meta! id="1"
public class EnvironsAgent extends Agent
{
    private Stat fansTimeInSystem;
    private Stat peopleLostStartMatch;
    
    private Stat peopleLostStartMatchLinkA;
    private Stat peopleLostStartMatchLinkB;
    private Stat peopleLostStartMatchLinkC;
    
    private ArrayList<ExponentialRNG> generatorsForBusStops;
    
    private TriangularRNG exitgGen;
    
    private int fansIncome;
    private int fansDeparted;
    private int fansDepartedLate;
    private int fansDepartedLateLinkA;
    private int fansDepartedLateLinkB;
    private int fansDepartedLateLinkC;
    
	public EnvironsAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
                
                generatorsForBusStops = new ArrayList<ExponentialRNG>();
                exitgGen = new TriangularRNG(0.6, 1.2, 4.2);
                
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
                fansTimeInSystem = new Stat();
                peopleLostStartMatch = new Stat();
                
                peopleLostStartMatchLinkA = new Stat();
                peopleLostStartMatchLinkB = new Stat();
                peopleLostStartMatchLinkC = new Stat();
                
                fansIncome = 0;
                fansDeparted = 0;
                fansDepartedLate = 0;
                
                fansDepartedLateLinkA = 0;
                fansDepartedLateLinkB = 0;
                fansDepartedLateLinkC = 0;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new EnvironsManager(Id.environsManager, mySim(), this);
		new CustomerExit(Id.customerExit, mySim(), this);
		new WaitToArriveStart(Id.waitToArriveStart, mySim(), this);
		new CustomerArrive(Id.customerArrive, mySim(), this);
		addOwnMessage(Mc.notice);
		addOwnMessage(Mc.requestResponseGetBusStops);
                
                addOwnMessage(Mc.customerLeave);
                addOwnMessage(Mc.allCustomersLeave);
                
                addOwnMessage(Mc.init);
                addOwnMessage(Mc.waitingForStartArriveEnd);
                addOwnMessage(Mc.startArriving);
                addOwnMessage(Mc.customerArrived);
                addOwnMessage(Mc.startExit);
	}
	//meta! tag="end"

    public ArrayList<ExponentialRNG> getGeneratorsForBusStops() {
        return generatorsForBusStops;
    }

    public TriangularRNG getExitgGen() {
        return exitgGen;
    }

    public void incFansIncome() {
        this.fansIncome++;
    }   

    public int getFansIncome() {
        return fansIncome;
    }
    
    public void incDepartedFans() {
        this.fansDeparted++;
    }

    public int getFansDeparted() {
        return fansDeparted;
    }
    
    public double getCounPeopleWhatLostMatch() {
        return ((double)(fansDepartedLate) / (double)fansIncome);
    }

    public double getCounPeopleWhatLostMatchLinkA() {
        return ((double)(fansDepartedLateLinkA) / (double)fansIncome);
    }
    
    public double getCounPeopleWhatLostMatchLinkB() {
        return ((double)(fansDepartedLateLinkB) / (double)fansIncome);
    }
    
    public double getCounPeopleWhatLostMatchLinkC() {
        return ((double)(fansDepartedLateLinkC) / (double)fansIncome);
    }
    
    public int getFansDepartedLate() {
        return fansDepartedLate;
    }
    public void incDepartedFansLate() {
        this.fansDepartedLate++;
    }

    public void incDepartedFansLateLinkA() {
        this.fansDepartedLateLinkA++;
    }
    
    public void incDepartedFansLateLinkB() {
        this.fansDepartedLateLinkB++;
    }
    
    public void incDepartedFansLateLinkC() {
        this.fansDepartedLateLinkC++;
    }
    
    public Stat getFansTimeInSystem() {
        return fansTimeInSystem;
    }

    public Stat getPeopleLostStartMatch() {
        return peopleLostStartMatch;
    }

    public Stat getPeopleLostStartMatchLinkA() {
        return peopleLostStartMatchLinkA;
    }

    public Stat getPeopleLostStartMatchLinkB() {
        return peopleLostStartMatchLinkB;
    }

    public Stat getPeopleLostStartMatchLinkC() {
        return peopleLostStartMatchLinkC;
    }

    public int getFansDepartedLateLinkA() {
        return fansDepartedLateLinkA;
    }

    public int getFansDepartedLateLinkB() {
        return fansDepartedLateLinkB;
    }

    public int getFansDepartedLateLinkC() {
        return fansDepartedLateLinkC;
    }
    
}
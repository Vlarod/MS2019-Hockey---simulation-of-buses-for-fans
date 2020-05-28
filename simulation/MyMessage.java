package simulation;

import Entities.Bus;
import Entities.BusStop;
import Entities.Fan;
import OSPABA.*;
import OSPDataStruct.SimQueue;
import java.util.ArrayList;

public class MyMessage extends MessageForm
{   
    private Bus myBus;
    private BusStop myNextBusStop; // zastavka pre autobusy
    private BusStop arriveMyBusStop; //zastavka pre prichody
    
    private ArrayList<BusStop> allBusStops;
    
    ArrayList<Fan> fansToExit;
    
    Fan myFan;
    private double fanStartWaiting;
    
    
    
	public MyMessage(Simulation sim)
	{
		super(sim);
	}

	public MyMessage(MyMessage original)
	{
		super(original);
		// copy() is called in superclass
	}

	@Override
	public MessageForm createCopy()
	{
		return new MyMessage(this);
	}

	@Override
	protected void copy(MessageForm message)
	{
		super.copy(message);
		MyMessage original = (MyMessage)message;
		// Copy attributes
                
                this.allBusStops = original.allBusStops;
                this.fansToExit = original.fansToExit;
                
                this.myFan = original.myFan;
                this.myBus = original.myBus;
                this.myNextBusStop = original.myNextBusStop;
                this.arriveMyBusStop = original.arriveMyBusStop;
                this.fanStartWaiting = original.fanStartWaiting;
	}


    public Bus getMyBus() {
        return myBus;
    }

    public void setMyBus(Bus myBus) {
        this.myBus = myBus;
    }

    public BusStop getMyNextBusStop() {
        return myNextBusStop;
    }

    public void setMyNextBusStop(BusStop myNextBusStop) {
        this.myNextBusStop = myNextBusStop;
    }

    public ArrayList<BusStop> getAllBusStops() {
        return allBusStops;
    }

    public void setAllBusStops(ArrayList<BusStop> allBusStops) {
        this.allBusStops = allBusStops;
    }

    public void setArriveMyBusStop(BusStop arriveMyBusStop) {
        this.arriveMyBusStop = arriveMyBusStop;
    }

    public BusStop getArriveMyBusStop() {
        return arriveMyBusStop;
    }

    public void setFanStartWaiting(double fanStartWaiting) {
        this.fanStartWaiting = fanStartWaiting;
    }

    public double getFanStartWaiting() {
        return fanStartWaiting;
    }

    public ArrayList<Fan> getFansToExit() {
        return fansToExit;
    }

    public void setFansToExit(ArrayList<Fan> fansToExit) {
        this.fansToExit = fansToExit;
    }

    public Fan getMyFan() {
        return myFan;
    }

    public void setMyFan(Fan myFan) {
        this.myFan = myFan;
    }
    
    
}
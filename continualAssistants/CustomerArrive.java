package continualAssistants;

import Entities.BusStop;
import Entities.Fan;
import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="45"
public class CustomerArrive extends Scheduler
{
	public CustomerArrive(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="EnvironsAgent", id="46", type="Start"
	public void processStart(MessageForm message)
	{
            message.setCode(Mc.customerArrived);
            int generatorId = ((MyMessage)message).getArriveMyBusStop().getglobalId();
            double nextHold = myAgent().getGeneratorsForBusStops().get(generatorId).sample();
            //hold(3, message); 
            hold(nextHold, message);            
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.customerArrived:
                        
                        MyMessage myMessage = (MyMessage)message.createCopy();
                        BusStop myBusStop = myMessage.getArriveMyBusStop();
                        myBusStop.incArrivedFans();
                        
                        
                        if(myBusStop.getArrivedFans() < myBusStop.getTravelersNumber()){ 
                            //if(myBusStop.getArrivedFans() < 1){ 
                            int generatorId = myBusStop.getglobalId();
                            double hlpTime = myBusStop.getDurationToStadion() + (10 * 60);
                            double nextHold = myAgent().getGeneratorsForBusStops().get(generatorId).sample();
                            if(mySim().currentTime()  + nextHold <= ((MySimulation)mySim()).getStartMatch() - hlpTime) {
                                //hold(3, myMessage);
                                hold(nextHold, myMessage);
                            } 
                        }
                        
                        
                        Fan myFan = new Fan(myAgent().getFansIncome());
                        myFan.setStartTimeInSystem(mySim().currentTime());
                        myAgent().incFansIncome();
                        ((MyMessage)message).setMyFan(myFan);
                        assistantFinished(message);
                    break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public EnvironsAgent myAgent()
	{
		return (EnvironsAgent)super.myAgent();
	}

}
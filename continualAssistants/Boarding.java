package continualAssistants;

import Entities.Bus;
import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;

//meta! id="59"
public class Boarding extends Process
{
	public Boarding(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BusStopAgent", id="60", type="Start"
	public void processStart(MessageForm message)
	{   
            
            message.setCode(Mc.customerBoarded);
            if(((MyMessage)message).getMyBus().getBusType() != 3){  
                //hold(1, message);
                hold(myAgent().getBoardingGen().sample(), message);
            } else {
                hold(myAgent().getBoardingGenMicroBus().sample(), message);
            }
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.customerBoarded:     
                        Bus myBus = ((MyMessage)message).getMyBus();
                        if(myBus.getStartTimeEmptyBus() != 0) {
                            myBus.setFullTimeEmptyBus(mySim().currentTime() - myBus.getStartTimeEmptyBus());
                            myBus.setStartTimeEmptyBus(0);
                        }
                        
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
	public BusStopAgent myAgent()
	{
		return (BusStopAgent)super.myAgent();
	}

}
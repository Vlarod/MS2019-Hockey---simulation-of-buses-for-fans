package continualAssistants;

import Entities.Bus;
import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="20"
public class RidingPlanning extends Scheduler
{
	public RidingPlanning(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BusAgent", id="21", type="Start"
	public void processStart(MessageForm message)
	{   
            MyMessage myMessage = (MyMessage)message;
            message.setCode(Mc.planningBusEnd);  
            
            if(myMessage.getMyBus().getMyLink() == "A") 
            {
            double holdTime = myMessage.getMyBus().getStartTime();            
            hold(holdTime, message);
            } 
            else if(myMessage.getMyBus().getMyLink() == "B") 
            {
            double holdTime = myMessage.getMyBus().getStartTime();            
            hold(holdTime, message);
            } 
            else if(myMessage.getMyBus().getMyLink() == "C") 
            {
            double holdTime = myMessage.getMyBus().getStartTime();            
            hold(holdTime, message);
            }
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{

                    case Mc.planningBusEnd:
                        ((MyMessage)message).getMyBus().setStartTimeEmptyBus(mySim().currentTime()); 
                        
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
	public BusAgent myAgent()
	{
		return (BusAgent)super.myAgent();
	}

}
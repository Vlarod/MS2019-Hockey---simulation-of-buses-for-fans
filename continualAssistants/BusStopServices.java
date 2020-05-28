package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;

//meta! id="30"
public class BusStopServices extends Process
{
	public BusStopServices(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BusAgent", id="31", type="Start"
	public void processStart(MessageForm message)
	{
            message.setCode(Mc.busStopServiceEnd);
            MyMessage myMessage = ((MyMessage)message);
            myMessage.getMyBus().setLastStop(myMessage.getMyNextBusStop());
            hold(0, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.busStopServiceEnd:
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
package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;

//meta! id="69"
public class BusWaitingOnBusStop extends Process
{
	public BusWaitingOnBusStop(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BusStopAgent", id="70", type="Start"
	public void processStart(MessageForm message)
	{
            ((MyMessage)message).getMyBus().setStatus("Cakanie");
            message.setCode(Mc.busWaitingOnBusStopEnd);
            hold(90, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.busWaitingOnBusStopEnd:
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

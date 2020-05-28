package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;

//meta! id="40"
public class Riding extends Process
{
	public Riding(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BusAgent", id="41", type="Start"
	public void processStart(MessageForm message)
	{
            message.setCode(Mc.ridingBusEnd);
            ((MyMessage)message).getMyBus().setStatus("Jazda");
            hold(((MyMessage)message).getMyNextBusStop().getRideLength(), message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.ridingBusEnd:
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
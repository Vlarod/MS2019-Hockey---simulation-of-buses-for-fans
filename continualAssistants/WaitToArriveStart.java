package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;

//meta! id="54"
public class WaitToArriveStart extends Process
{
	public WaitToArriveStart(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="EnvironsAgent", id="55", type="Start"
	public void processStart(MessageForm message)
	{
            double startMatch = ((MySimulation)mySim()).getStartMatch();
            double startSimulation = ((MySimulation)mySim()).getStartSimulation();
            double durationToStadion = ((MyMessage)message).getArriveMyBusStop().getDurationToStadion();
            double holdTime = (startMatch - startSimulation) - (durationToStadion + 75 * 60);
            message.setCode(Mc.waitingForStartArriveEnd);
            hold(holdTime, message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.waitingForStartArriveEnd:
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
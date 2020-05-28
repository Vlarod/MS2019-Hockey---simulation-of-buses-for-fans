package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="64"
public class CustomerExit extends Scheduler
{
	public CustomerExit(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="EnvironsAgent", id="65", type="Start"
	public void processStart(MessageForm message)
	{
            message.setCode(Mc.customerLeave);
            
            if(((MyMessage)message).getMyBus().getBusType() != 3){
                //hold(1, message);
                hold(myAgent().getExitgGen().sample(), message);
            } else {
                hold(4, message);
            }
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.customerLeave:
                        ((MyMessage)message).getMyFan().setFullTimeInSystem(mySim().currentTime() - ((MyMessage)message).getMyFan().getStartTimeInSystem());
                        myAgent().getFansTimeInSystem().addSample(((MyMessage)message).getMyFan().getFullTimeInSystem());
                        
                        myAgent().incDepartedFans();
                        
                        if(mySim().currentTime() > ((MySimulation)mySim()).getStartMatch()) {
                            myAgent().incDepartedFansLate();
                            if(((MyMessage)message).getMyBus().getMyLink() == "A") {
                                myAgent().incDepartedFansLateLinkA();
                            } else if(((MyMessage)message).getMyBus().getMyLink() == "B") {
                                myAgent().incDepartedFansLateLinkB();
                            } else if(((MyMessage)message).getMyBus().getMyLink() == "C") {
                                myAgent().incDepartedFansLateLinkC();
                            } 
                        }
                        
                        if(myAgent().getFansIncome() == myAgent().getFansDeparted()){
                            mySim().stopReplication();
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
	public EnvironsAgent myAgent()
	{
		return (EnvironsAgent)super.myAgent();
	}

}
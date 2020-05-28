package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
//import instantAssistants.*;

//meta! id="2"
public class ModelManager extends Manager
{
	public ModelManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="BusStopAgent", id="15", type="Response"
	public void processRequestResponseBusStopAgent(MessageForm message)
	{
            response(message);
	}

	//meta! sender="BusAgent", id="14", type="Request"
	public void processRequestResponseBusAgent(MessageForm message)
	{
            message.setAddressee(mySim().findAgent(Id.busStopAgent));
            message.setCode(Mc.requestResponse);
            request(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.init:
                        startWaitToStart();
                    break; 
                    
                    case Mc.customerArrived:
                        message.setAddressee(mySim().findAgent(Id.busStopAgent));
                        notice(message);
                    break;
                    
                    case Mc.busStopService:
                        message.setAddressee(mySim().findAgent(Id.busStopAgent));
                        notice(message);
                    break;
                    
                    case Mc.busStopServiceEnd:
                        message.setAddressee(mySim().findAgent(Id.busAgent));
                        notice(message);
                    break;
                    
                    case Mc.startExit:
                        message.setAddressee(mySim().findAgent(Id.environsAgent));
                        notice(message);
                    break;
                    
                    case Mc.allCustomersLeave:
                        message.setAddressee(mySim().findAgent(Id.busAgent));
                        notice(message);
                    break;
                    
                    case Mc.customerBoarded:
                        message.setAddressee(mySim().findAgent(Id.busAgent));
                        notice(message);
                    break;
		}
	}

	//meta! sender="BusStopAgent", id="36", type="Response"
	public void processRequestResponseGetCurrBusStopBusStopAgent(MessageForm message)
	{
            response(message);
	}

	//meta! sender="BusAgent", id="35", type="Request"
	public void processRequestResponseGetCurrBusStopBusAgent(MessageForm message)
	{
            message.setAddressee(mySim().findAgent(Id.busStopAgent));
            request(message);
	}

	//meta! sender="BusStopAgent", id="48", type="Response"
	public void processRequestResponseGetBusStopsBusStopAgent(MessageForm message)
	{
            response(message);
	}

	//meta! sender="EnvironsAgent", id="47", type="Request"
	public void processRequestResponseGetBusStopsEnvironsAgent(MessageForm message)
	{
            message.setAddressee(Id.busStopAgent);
            request(message);
	}

	//meta! sender="WaitToStart", id="52", type="Finish"
	public void processFinish(MessageForm message)
	{
            message.setCode(Mc.init);
            
            MyMessage messageEnvirons = (MyMessage)message.createCopy();
                        
            message.setAddressee(mySim().findAgent(Id.busAgent));
            notice(message);
                        
            message.setCode(Mc.init);
            messageEnvirons.setAddressee(mySim().findAgent(Id.environsAgent));
            notice(messageEnvirons);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.requestResponseGetCurrBusStop:
			switch (message.sender().id())
			{
			case Id.busStopAgent:
				processRequestResponseGetCurrBusStopBusStopAgent(message);
			break;

			case Id.busAgent:
				processRequestResponseGetCurrBusStopBusAgent(message);
			break;
			}
		break;

		case Mc.requestResponse:
			switch (message.sender().id())
			{
			case Id.busAgent:
				processRequestResponseBusAgent(message);
			break;

			case Id.busStopAgent:
				processRequestResponseBusStopAgent(message);
			break;
			}
		break;

		case Mc.finish:
			processFinish(message);
		break;

		case Mc.requestResponseGetBusStops:
			switch (message.sender().id())
			{
			case Id.environsAgent:
				processRequestResponseGetBusStopsEnvironsAgent(message);
			break;

			case Id.busStopAgent:
				processRequestResponseGetBusStopsBusStopAgent(message);
			break;
			}
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public ModelAgent myAgent()
	{
		return (ModelAgent)super.myAgent();
	}

        public void startWaitToStart() {
         MyMessage message = new MyMessage(_mySim);
         message.setAddressee(myAgent().findAssistant(Id.waitToStart));
         startContinualAssistant(message);
        }
}
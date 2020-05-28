package managers;

import Entities.Bus;
import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
//import instantAssistants.*;

//meta! id="3"
public class BusManager extends Manager
{
	public BusManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="11", type="Notice"
	public void processNotice(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processFinishRidingPlanning_C(MessageForm message)
	{
	}

	//meta! sender="RidingPlanning", id="21", type="Finish"
	public void processFinishRidingPlanning(MessageForm message)
	{
            message.setCode(Mc.requestResponseGetCurrBusStop);
            message.setAddressee(mySim().findAgent(Id.modelAgent));
            request(message);
	}

	//meta! userInfo="Removed from model"
	public void processFinishRidingPlanning_B(MessageForm message)
	{
	}

	//meta! sender="ModelAgent", id="14", type="Response"
	public void processRequestResponse(MessageForm message)
	{
            message.setCode(Mc.startBusPLanning);
            message.setAddressee(this);
            notice(message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.init:
                        message.setCode(Mc.startBusPLanning);
                        notice(message);
                    break;
                    
                    case Mc.startBusPLanning:
                        for(int i = 0; i < myAgent().getBuses_A().size(); i++){
                            MyMessage newMessage = ((MyMessage)message.createCopy());
                            Bus myBus = myAgent().getBuses_A().get(i);
                            newMessage.setMyBus(myBus);
                            this.startPlanningRiding(newMessage);
                        }
                        
                        for(int i = 0; i < myAgent().getBuses_B().size(); i++){
                            MyMessage newMessage = ((MyMessage)message.createCopy());
                            Bus myBus = myAgent().getBuses_B().get(i);
                            newMessage.setMyBus(myBus);
                            this.startPlanningRiding(newMessage);
                        }
                        
                        for(int i = 0; i < myAgent().getBuses_C().size(); i++){
                            MyMessage newMessage = ((MyMessage)message.createCopy());
                            Bus myBus = myAgent().getBuses_C().get(i);
                            newMessage.setMyBus(myBus);
                            this.startPlanningRiding(newMessage);
                        }
                        
                    break;
                    
                    case Mc.ridingBus:
                        startRiding((MyMessage)message);
                    break;
                    
                    case Mc.busStopService:
                        MyMessage myMessage = ((MyMessage)message);
                        Bus myBus = myMessage.getMyBus();

                        if(myBus.isIsFull() != true && ((MyMessage)message).getMyNextBusStop().getType() != "ST") 
                        {
                            message.setAddressee(mySim().findAgent(Id.modelAgent));
                            myBus.setLastStop(myMessage.getMyNextBusStop());
                            myBus.setStatus("Nastup");
                            notice(message);
                        } else if (myBus.isIsFull() == true && myMessage.getMyNextBusStop().getType() != "ST"){
                            myBus.setLastStop(myMessage.getMyNextBusStop());
                            message.setCode(Mc.busStopServiceEnd);
                            message.setAddressee(this);
                            notice(message);
                        } else if(myMessage.getMyNextBusStop().getType() == "ST") {
                            myMessage.setFansToExit(myAgent().getFansInBusAllBuses().get(myBus.getGlobalId()));
                            myBus.setLastStop(myMessage.getMyNextBusStop());
                            myBus.setStatus("Vystup");
                            myMessage.setCode(Mc.startExit);
                            myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                            notice(myMessage);
                        }
                    break;
                    
                    case Mc.busStopServiceEnd:
                        message.setCode(Mc.ridingBus);
                        message.setAddressee(this);
                        notice(message);
                    break;
                    
                    case Mc.allCustomersLeave:
                        message.setCode(Mc.ridingBus);
                        message.setAddressee(this);
                        notice(message);
                    break;
                    
                    case Mc.customerBoarded:
                        MyMessage customer = ((MyMessage)message);
                        myAgent().getFansInBusAllBuses().get(customer.getMyBus().getGlobalId()).add(customer.getMyFan());
                    break;
		}
	}

	//meta! sender="BusStopServices", id="31", type="Finish"
	public void processFinishBusStopServices(MessageForm message)
	{           
            message.setCode(Mc.ridingBus);
            message.setAddressee(this);
            notice(message);
	}

	//meta! sender="ModelAgent", id="35", type="Response"
	public void processRequestResponseGetCurrBusStop(MessageForm message)
	{
            message.setCode(Mc.busStopService);
            message.setAddressee(this);
            notice(message);
	}

	//meta! sender="Riding", id="41", type="Finish"
	public void processFinishRiding(MessageForm message)
	{
            message.setCode(Mc.requestResponseGetCurrBusStop);
            message.setAddressee(mySim().findAgent(Id.modelAgent));
            request(message);
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
		case Mc.requestResponse:
			processRequestResponse(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.riding:
				processFinishRiding(message);
			break;

			case Id.busStopServices:
				processFinishBusStopServices(message);
			break;

			case Id.ridingPlanning:
				processFinishRidingPlanning(message);
			break;
			}
		break;

		case Mc.notice:
			processNotice(message);
		break;

		case Mc.requestResponseGetCurrBusStop:
			processRequestResponseGetCurrBusStop(message);
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
        
        public void startPlanningRiding(MyMessage initMessage)
        {
         MyMessage message = initMessage;
         message.setAddressee(myAgent().findAssistant(Id.ridingPlanning));
         startContinualAssistant(message);
        }
        
        public void startRiding(MyMessage initMessage) {
         MyMessage message = initMessage;
         message.setAddressee(myAgent().findAssistant(Id.riding));
         startContinualAssistant(message);            
        }
        
        public void startBusStopService(MyMessage initMessage) {
         MyMessage message = initMessage;
         message.setAddressee(myAgent().findAssistant(Id.busStopServices));
         startContinualAssistant(message);         
        }
}
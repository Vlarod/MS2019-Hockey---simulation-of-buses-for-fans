package managers;

import Entities.Bus;
import Entities.BusStop;
import OSPABA.*;
import OSPRNG.ExponentialRNG;
import simulation.*;
import agents.*;
import continualAssistants.*;
import java.util.ArrayList;
//import instantAssistants.*;

//meta! id="1"
public class EnvironsManager extends Manager
{
	public EnvironsManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="13", type="Notice"
	public void processNotice(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.init:
                        message.setCode(Mc.requestResponseGetBusStops);
                        message.setAddressee(mySim().findAgent(Id.modelAgent));
                        request(message);
                    break;
                    
                    case Mc.startArriving:
                        startAgentArrive((MyMessage)message);
                    break;
                    
                    case Mc.customerArrived:
                        message.setAddressee(mySim().findAgent(Id.modelAgent));
                        notice(message);
                    break;
                    
                    case Mc.startExit:
                            
                            MyMessage myMessage = (MyMessage)message;
                            Bus myBus = myMessage.getMyBus();
                            myBus.setClodedDoor(0);
                            
                            for (int i = 0; i < myBus.getDoors(); i++){
                                if(myBus.getSeatsFull() != 0) 
                                {     
                                    MyMessage fan = (MyMessage)myMessage.createCopy();
                                    fan.setMyFan(myMessage.getFansToExit().get(0));
                                    myMessage.getFansToExit().remove(0);
                                    fan.getMyBus().descSeatsFull();
                                    startAgentExit(fan);  
                                } else {
                                    myBus.incClosedDoor();
                                }
                    
                            }
                            if(myMessage.getMyBus().getClodedDoor() == myMessage.getMyBus().getDoors()) {
                                myMessage.getMyBus().setIsFull(false);
                                myMessage.getMyBus().setStartTimeEmptyBus(mySim().currentTime());
                                
                                message.setCode(Mc.allCustomersLeave);
                                message.setAddressee(mySim().findAgent(Id.modelAgent));

                                notice(message);
                            }
                    break;
		}
	}

	//meta! sender="CustomerArrive", id="46", type="Finish"
	public void processFinishCustomerArrive(MessageForm message)
	{
            message.setCode(Mc.customerArrived);
            notice(message);
	}

	//meta! sender="ModelAgent", id="47", type="Response"
	public void processRequestResponseGetBusStops(MessageForm message)
	{
            startPlanningArrives((MyMessage)message);
	}

	//meta! sender="WaitToArriveStart", id="55", type="Finish"
	public void processFinishWaitToArriveStart(MessageForm message)
	{
            message.setCode(Mc.startArriving);
            message.setAddressee(this);
            notice(message);
	}

	//meta! sender="CustomerExit", id="65", type="Finish"
	public void processFinishCustomerExit(MessageForm message)
	{
            MyMessage myMessage = (MyMessage)message;
            Bus myBus = myMessage.getMyBus();

                if(myBus.getSeatsFull() != 0) 
                   {
                        MyMessage fan = (MyMessage)myMessage.createCopy();
                        fan.setMyFan(myMessage.getFansToExit().get(0));
                        myMessage.getFansToExit().remove(0);
                        fan.getMyBus().descSeatsFull();
                        startAgentExit(fan);  
                    } else {
                        myBus.incClosedDoor();
                    }
                    
                
                if(myMessage.getMyBus().getClodedDoor() == myMessage.getMyBus().getDoors()) {
                    myMessage.getMyBus().setIsFull(false);
                    myMessage.getMyBus().setStartTimeEmptyBus(mySim().currentTime());
                    
                    myMessage.setCode(Mc.allCustomersLeave);
                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                    
                    //MyMessage newMessage = (MyMessage)myMessage.createCopy();
                    notice(myMessage);
                }
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
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.customerExit:
				processFinishCustomerExit(message);
			break;

			case Id.customerArrive:
				processFinishCustomerArrive(message);
			break;

			case Id.waitToArriveStart:
				processFinishWaitToArriveStart(message);
			break;
			}
		break;

		case Mc.requestResponseGetBusStops:
			processRequestResponseGetBusStops(message);
		break;

		case Mc.notice:
			processNotice(message);
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
        
        public void startAgentArrive(MyMessage message) {
                message.setAddressee(myAgent().findAssistant(Id.customerArrive));
                startContinualAssistant(message); 
        }
        
        public void startAgentExit(MyMessage message) {
                message.setAddressee(myAgent().findAssistant(Id.customerExit));
                startContinualAssistant(message); 
        }
        
        public void startPlanningArrives(MyMessage message) {
            ArrayList<BusStop> allBusStops = message.getAllBusStops();
            
            for(int i = 0; i < allBusStops.size() - 1; i++)
            {              
                double hlp = ((double)65 * (double)60) / ((double)allBusStops.get(i).getTravelersNumber());
                ExponentialRNG _exp = new ExponentialRNG(hlp);
                myAgent().getGeneratorsForBusStops().add(_exp);
                allBusStops.get(i).setglobalId(i);
                
                MyMessage newMessage = new MyMessage(mySim());
                newMessage.setArriveMyBusStop(allBusStops.get(i));
                newMessage.setAddressee(myAgent().findAssistant(Id.waitToArriveStart));
                startContinualAssistant(newMessage); 
            }
        }
        
}
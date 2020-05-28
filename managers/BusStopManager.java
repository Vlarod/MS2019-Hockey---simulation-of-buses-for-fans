package managers;

import Entities.Bus;
import Entities.BusStop;
import OSPABA.*;
import OSPDataStruct.SimQueue;
import simulation.*;
import agents.*;
import continualAssistants.*;
//import instantAssistants.*;

//meta! id="4"
public class BusStopManager extends Manager
{
	public BusStopManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="12", type="Notice"
	public void processNotice(MessageForm message)
	{
	}

	//meta! sender="ModelAgent", id="15", type="Request"
	public void processRequestResponse(MessageForm message)
	{
 
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
                    case Mc.customerArrived:
                        BusStop myBusStop = ((MyMessage)message).getArriveMyBusStop();                       
                        myAgent().getFansAllLinks().get(myBusStop.getglobalId()).add(message); 
                        ((MyMessage)message).setFanStartWaiting(mySim().currentTime());
                        
                        if(((MySimulation)mySim()).isWaitingMode() == true && myAgent().getBusesOnBusStops().get(myBusStop.getglobalId()).size() != 0) {
                            MyMessage myBS = (MyMessage)myAgent().getBusesOnBusStops().get(myBusStop.getglobalId()).get(0).createCopy();
                            myBS.setCode(Mc.busStopService);
                            myBS.setAddressee(this);
                            notice(myBS);
                        }
                    break;
                    
                    case Mc.busStopService:
                        MyMessage myMessage = ((MyMessage)message);
                        Bus myBus = ((MyMessage)message).getMyBus();
                        int idMyStop = myBus.getLastStop().getglobalId();  
                         
                         for(int i = 0; i < myBus.getDoors(); i++) {
                            if(myBus.isIsFull() == false) {
                                if(myAgent().getFansAllLinks().get(idMyStop).size() != 0)  
                                {
                                    if(myBus.getDoors() == 1) {
                                        if(myBus.getClodedDoor() > 0) {
                                            MyMessage waitingFan = myAgent().getFirstFanToMicroBus(myAgent().getFansAllLinks().get(idMyStop), mySim().currentTime());
                                            if(waitingFan != null) {
                                                //MyMessage waitingFan = (MyMessage)myAgent().getFansAllLinks().get(idMyStop).dequeue();
                                                double waitingTime = mySim().currentTime() - waitingFan.getFanStartWaiting();
                                                 myAgent().getWaitingTimeStat().addSample(waitingTime);
                                            
                                                    if(myBus.getMyLink() == "A") {
                                                        myAgent().setWaitingTimeLinkASum(waitingTime);
                                                    } else if(myBus.getMyLink() == "B") {
                                                         myAgent().setWaitingTimeLinkBSum(waitingTime);
                                                    } else if(myBus.getMyLink() == "C") {
                                                         myAgent().setWaitingTimeLinkCSum(waitingTime);
                                                    } 
                                                
                                                MyMessage copy = (MyMessage)myMessage.createCopy();
                                                copy.setMyFan(waitingFan.getMyFan());
                                                copy.getMyBus().incSeatsFul();
                                                copy.getMyBus().descClosedDoor();
                                                copy.getMyBus().incProfit();
                                                if(myBus.getMaxSeats() == myBus.getSeatsFull()) {
                                                    myBus.setIsFull(true);
                                                }
                                                startBoarding(copy);
                                            }
                                        }    

                                    } else if(myBus.getDoors() > 1){
                                        if(myBus.getClodedDoor() > 0) {
                                            MyMessage waitingFan = (MyMessage)myAgent().getFansAllLinks().get(idMyStop).dequeue();
                                            
                                            double waitingTime = mySim().currentTime() - waitingFan.getFanStartWaiting();
                                            myAgent().getWaitingTimeStat().addSample(waitingTime);
                                            
                                                    if(myBus.getMyLink() == "A") {
                                                        myAgent().setWaitingTimeLinkASum(waitingTime);
                                                    } else if(myBus.getMyLink() == "B") {
                                                         myAgent().setWaitingTimeLinkBSum(waitingTime);
                                                    } else if(myBus.getMyLink() == "C") {
                                                         myAgent().setWaitingTimeLinkCSum(waitingTime);
                                                    } 
                                            
                                            MyMessage copy = (MyMessage)myMessage.createCopy();
                                            copy.setMyFan(waitingFan.getMyFan());
                                            copy.getMyBus().incSeatsFul();
                                            copy.getMyBus().descClosedDoor();

                                            if(myBus.getMaxSeats() == myBus.getSeatsFull()) {
                                                myBus.setIsFull(true);
                                            }
                                            startBoarding(copy);   
                                        }
                                    }
                                } 
                            } 
                         }
                         
                        if(myBus.getClodedDoor() == myBus.getDoors()){
                           if(((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsInBusStop() == false){
                                 if(myAgent().getBusesOnBusStops().get(idMyStop).size() > 0) {
                                    myBus.setIsWaitingInBusStopEnd(false);
                                    myBus.setIsInBusStop(false);
                                    myMessage.setCode(Mc.busStopServiceEnd);
                                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                                    notice(myMessage);
                                  } else {
                                    myBus.setIsInBusStop(true);

                                    MyMessage waitMessage = (MyMessage)message.createCopy();
                                    myAgent().getBusesOnBusStops().get(idMyStop).add(waitMessage);

                                    myMessage.setAddressee(myAgent().findAssistant(Id.busWaitingOnBusStop));
                                    startContinualAssistant(myMessage); 
                                }

                            } else if (((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsWaitingInBusStopEnd() == true){
                                myBus.setIsWaitingInBusStopEnd(false);
                                myBus.setIsInBusStop(false);
                                myMessage.setCode(Mc.busStopServiceEnd);
                                myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                                notice(myMessage);

                            } else if (((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsFull() == true){
                                SimQueue< MessageForm > buses = myAgent().getBusesOnBusStops().get(idMyStop);
                                int removeId = getIndexOfBusOnBusStop(buses, myBus);
                                myAgent().getBusesOnBusStops().get(idMyStop).remove(removeId);
                    
                                myBus.setIsWaitingInBusStopEnd(false);
                                myBus.setIsInBusStop(false);
                                myMessage.setCode(Mc.busStopServiceEnd);
                                myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                                notice(myMessage);

                            } else if (((MySimulation)mySim()).isWaitingMode() == false){
                                myMessage.setCode(Mc.busStopServiceEnd);
                                myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                                notice(myMessage);
                            }                
                        }
                    break;
		}
	}

        public int getIndexOfBusOnBusStop(SimQueue< MessageForm > buses, Bus bus) {
            for(int i = 0; i < buses.size(); i++) {
                if(((MyMessage)buses.get(i)).getMyBus().getId() == bus.getId()) {
                    return i;
                }
            }
            return -1;
        }
        
	//meta! sender="ModelAgent", id="36", type="Request"
	public void processRequestResponseGetCurrBusStop(MessageForm message)
	{
            MyMessage myMessage = ((MyMessage)message);
            Bus myBus = myMessage.getMyBus();
            if(myBus.getMyLink() == "A"){
                int index = myMessage.getMyBus().getIdNextStop();
                myBus.incIdNextStop();
                if(myBus.getIdNextStop() == myAgent().getBusStops_A().size()){
                    myBus.setIdNextStop(0);
                }
                BusStop myNextBusStop = myAgent().getBusStops_A().get(index);
                myMessage.setMyNextBusStop(myNextBusStop);
                response(message);
            } else if(myMessage.getMyBus().getMyLink() == "B") {
                int index = myMessage.getMyBus().getIdNextStop();
                myBus.incIdNextStop();
                if(myBus.getIdNextStop() == myAgent().getBusStops_B().size()){
                    myBus.setIdNextStop(0);
                }
                BusStop myNextBusStop = myAgent().getBusStops_B().get(index);
                myMessage.setMyNextBusStop(myNextBusStop);
                response(message);
            } else if(myMessage.getMyBus().getMyLink() == "C") {
                int index = myMessage.getMyBus().getIdNextStop();
                myBus.incIdNextStop();
                if(myBus.getIdNextStop() == myAgent().getBusStops_C().size()){
                    myBus.setIdNextStop(0);
                }
                BusStop myNextBusStop = myAgent().getBusStops_C().get(index);
                myMessage.setMyNextBusStop(myNextBusStop);
                response(message);
            }
	}

	//meta! sender="ModelAgent", id="48", type="Request"
	public void processRequestResponseGetBusStops(MessageForm message)
	{
            ((MyMessage)message).setAllBusStops(myAgent().getAllBusStops());

            response(message);
	}

	//meta! sender="Boarding", id="60", type="Finish"
	public void processFinishBoarding(MessageForm message)
	{     
            
            MyMessage myMessage = ((MyMessage)message);
            
            //nalozenie fanucika do autobusu
            MyMessage fan = (MyMessage)myMessage.createCopy();
            fan.setCode(Mc.customerBoarded);
            fan.setAddressee(mySim().findAgent(Id.modelAgent));
            notice(fan);
            //
            
            Bus myBus = ((MyMessage)message).getMyBus();
            int idMyStop = myBus.getLastStop().getglobalId();
            myBus.incClosedDoor();
            
            if(myBus.isIsFull() != true) 
            {      
                    if(myAgent().getFansAllLinks().get(idMyStop).size() != 0)  
                                {   
                                    if(myBus.getDoors() == 1){
                                        if(myBus.getClodedDoor() > 0) {
                                         MyMessage waitingFan = myAgent().getFirstFanToMicroBus(myAgent().getFansAllLinks().get(idMyStop), mySim().currentTime());
                                            if(waitingFan != null) {
                                                //MyMessage waitingFan = (MyMessage)myAgent().getFansAllLinks().get(idMyStop).dequeue();
                                                double waitingTime = mySim().currentTime() - waitingFan.getFanStartWaiting();
                                                myAgent().getWaitingTimeStat().addSample(waitingTime);
                                            
                                                    if(myBus.getMyLink() == "A") {
                                                        myAgent().setWaitingTimeLinkASum(waitingTime);
                                                    } else if(myBus.getMyLink() == "B") {
                                                         myAgent().setWaitingTimeLinkBSum(waitingTime);
                                                    } else if(myBus.getMyLink() == "C") {
                                                         myAgent().setWaitingTimeLinkCSum(waitingTime);
                                                    } 
                                                
                                                MyMessage copy = (MyMessage)myMessage.createCopy();
                                                copy.setMyFan(waitingFan.getMyFan());
                                                copy.getMyBus().incSeatsFul();
                                                copy.getMyBus().descClosedDoor();
                                                copy.getMyBus().incProfit();
                                                
                                                if(myBus.getMaxSeats() == myBus.getSeatsFull()) {
                                                    myBus.setIsFull(true);
                                                }
                                                startBoarding(copy);
                                            } 
                                        }
                                        
                                    } else if (myBus.getDoors() > 1){
                                        if(myBus.getClodedDoor() > 0) {
                                            MyMessage waitingFan = (MyMessage)myAgent().getFansAllLinks().get(idMyStop).dequeue();
                                            
                                            double waitingTime = mySim().currentTime() - waitingFan.getFanStartWaiting();
                                            myAgent().getWaitingTimeStat().addSample(waitingTime);
                                            
                                                   if(myBus.getMyLink() == "A") {
                                                        myAgent().setWaitingTimeLinkASum(waitingTime);
                                                    } else if(myBus.getMyLink() == "B") {
                                                         myAgent().setWaitingTimeLinkBSum(waitingTime);
                                                    } else if(myBus.getMyLink() == "C") {
                                                         myAgent().setWaitingTimeLinkCSum(waitingTime);
                                                    } 
                                            
                                            MyMessage copy = (MyMessage)myMessage.createCopy();
                                            copy.getMyBus().incSeatsFul();
                                            copy.getMyBus().descClosedDoor();

                                            if(myBus.getMaxSeats() == myBus.getSeatsFull()) {
                                                myBus.setIsFull(true);
                                            }

                                            startBoarding(copy);
                                        }   
                                    }
                                } 
            } 
            
            if(myBus.getClodedDoor() == myBus.getDoors()){
                if(((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsInBusStop() == false){
                    if(myAgent().getBusesOnBusStops().get(idMyStop).size() > 0) {
                        myBus.setIsWaitingInBusStopEnd(false);
                        myBus.setIsInBusStop(false);
                        myMessage.setCode(Mc.busStopServiceEnd);
                        myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                        notice(myMessage);
                    } else {
                        myBus.setIsInBusStop(true);

                        MyMessage waitMessage = (MyMessage)message.createCopy();
                        myAgent().getBusesOnBusStops().get(idMyStop).add(waitMessage);

                        myMessage.setAddressee(myAgent().findAssistant(Id.busWaitingOnBusStop));
                        startContinualAssistant(myMessage); 
                    }
                    
                } else if (((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsWaitingInBusStopEnd() == true){
                    myBus.setIsWaitingInBusStopEnd(false);
                    myBus.setIsInBusStop(false);
                    myMessage.setCode(Mc.busStopServiceEnd);
                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                    notice(myMessage);
                    
                } else if (((MySimulation)mySim()).isWaitingMode() == true && myBus.isIsFull() == true){
                    SimQueue< MessageForm > buses = myAgent().getBusesOnBusStops().get(idMyStop);
                    int removeId = getIndexOfBusOnBusStop(buses, myBus);
                    myAgent().getBusesOnBusStops().get(idMyStop).remove(removeId);
                    
                    myBus.setIsWaitingInBusStopEnd(false);
                    myBus.setIsInBusStop(false);
                    myMessage.setCode(Mc.busStopServiceEnd);
                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                    notice(myMessage);
                    
                } else if (((MySimulation)mySim()).isWaitingMode() == false){
                    myMessage.setCode(Mc.busStopServiceEnd);
                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                    notice(myMessage);
                }
            }
	
        }
	//meta! sender="BusWaitingOnBusStop", id="70", type="Finish"
	public void processFinishBusWaitingOnBusStop(MessageForm message)
	{
            
            MyMessage myMessage = ((MyMessage)message);
            Bus myBus = ((MyMessage)message).getMyBus();
            
            if(myBus.isIsInBusStop() == true) {
                myBus.setIsWaitingInBusStopEnd(true);

                int idMyStop = myBus.getLastStop().getglobalId();
                SimQueue< MessageForm > buses = myAgent().getBusesOnBusStops().get(idMyStop);
                int removeId = getIndexOfBusOnBusStop(buses, myBus);
                if(myAgent().getBusesOnBusStops().get(idMyStop).size() == 0) {
                        int a = 0;
                }
                myAgent().getBusesOnBusStops().get(idMyStop).remove(removeId);

                if(myBus.getDoors() == myBus.getClodedDoor()) {
                    myBus.setIsWaitingInBusStopEnd(false);
                    myBus.setIsInBusStop(false);

                    myMessage.setCode(Mc.busStopServiceEnd);
                    myMessage.setAddressee(mySim().findAgent(Id.modelAgent));
                    notice(myMessage);
                }
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
		case Mc.requestResponseGetCurrBusStop:
			processRequestResponseGetCurrBusStop(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.busWaitingOnBusStop:
				processFinishBusWaitingOnBusStop(message);
			break;

			case Id.boarding:
				processFinishBoarding(message);
			break;
			}
		break;

		case Mc.requestResponse:
			processRequestResponse(message);
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
	public BusStopAgent myAgent()
	{
		return (BusStopAgent)super.myAgent();
	}

        public void startBoarding(MyMessage message) {
         message.setAddressee(myAgent().findAssistant(Id.boarding));
         startContinualAssistant(message);             
        }
}
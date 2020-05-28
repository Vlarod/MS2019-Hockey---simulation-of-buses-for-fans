package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;
//import instantAssistants.*;

//meta! id="2"
public class ModelAgent extends Agent
{
	public ModelAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ModelManager(Id.modelManager, mySim(), this);
		new WaitToStart(Id.waitToStart, mySim(), this);
		addOwnMessage(Mc.requestResponse);
		addOwnMessage(Mc.requestResponseGetBusStops);
		addOwnMessage(Mc.requestResponseGetCurrBusStop);
                
                addOwnMessage(Mc.allCustomersLeave);
                addOwnMessage(Mc.init);
                addOwnMessage(Mc.startSimulate);  
                addOwnMessage(Mc.customerArrived);
                addOwnMessage(Mc.busStopService);
                addOwnMessage(Mc.busStopServiceEnd);
                addOwnMessage(Mc.startExit);
                addOwnMessage(Mc.customerBoarded);
	}
	//meta! tag="end"
 
        public void startSimulation(){
            MyMessage message = new MyMessage(mySim());
            message.setCode(Mc.init);
            message.setAddressee(this);
            manager().notice(message);
        }
}
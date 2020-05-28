package simulation;

import Entities.Bus;
import OSPABA.*;
import OSPStat.Stat;
import agents.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySimulation extends Simulation
{
        private double startMatch;
        private double startSimulation;
        private boolean waitingMode;
        private boolean simulationMode;
        
        private Stat averageWaitingTimeRepl;
        private Stat peopleLostStartMatchRepl;
        private Stat fansFullTimeInSystem;
        
        private Stat emptyBusesLinkATimeRepl;
        private Stat emptyBusesLinkBTimeRepl;
        private Stat emptyBusesLinkCTimeRepl;
        
        private Stat fansDepartedLateLinkARepl;
        private Stat fansDepartedLateLinkBRepl;
        private Stat fansDepartedLateLinkCRepl;
        
        private Stat averageWaitingTimeLinkARepl;
        private Stat averageWaitingTimeLinkBRepl;
        private Stat averageWaitingTimeLinkCRepl;
        
        private Stat averageProfitMicrobuses;
         
	public MySimulation() throws FileNotFoundException, UnsupportedEncodingException
	{  
		init();
                
                
	}

	@Override
	public void prepareSimulation()
	{
		super.prepareSimulation();
		// Create global statistcis
                peopleLostStartMatchRepl = new Stat();
                averageWaitingTimeRepl = new Stat();
                fansFullTimeInSystem = new Stat();
                
                emptyBusesLinkATimeRepl = new Stat();
                emptyBusesLinkBTimeRepl = new Stat();
                emptyBusesLinkCTimeRepl = new Stat();
                
                fansDepartedLateLinkARepl = new Stat();
                fansDepartedLateLinkBRepl = new Stat();
                fansDepartedLateLinkCRepl = new Stat();
                
                averageWaitingTimeLinkARepl = new Stat();
                averageWaitingTimeLinkBRepl = new Stat();
                averageWaitingTimeLinkCRepl = new Stat();
                
                averageProfitMicrobuses = new Stat();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Reset entities, queues, local statistics, etc...
                
                calculateStartSimulation();
                modelAgent().startSimulation();               
	}

	@Override
	public void replicationFinished()
	{   
		// Collect local statistics into global, update UI, etc...
		super.replicationFinished();
                
                busAgent().calculateProfitMicrobuses();
                averageProfitMicrobuses.addSample(busAgent().getProfitForMicrobuses());
                
                environsAgent().getPeopleLostStartMatch().addSample(environsAgent().getCounPeopleWhatLostMatch());
                environsAgent().getPeopleLostStartMatchLinkA().addSample(environsAgent().getCounPeopleWhatLostMatchLinkA());
                environsAgent().getPeopleLostStartMatchLinkB().addSample(environsAgent().getCounPeopleWhatLostMatchLinkB());
                environsAgent().getPeopleLostStartMatchLinkC().addSample(environsAgent().getCounPeopleWhatLostMatchLinkC());
                
                busStopAgent().getWaitingTimeStatLinkA().addSample(busStopAgent().getWaitingTimeLinkASum()/environsAgent().getFansDeparted());
                busStopAgent().getWaitingTimeStatLinkB().addSample(busStopAgent().getWaitingTimeLinkBSum()/environsAgent().getFansDeparted());
                busStopAgent().getWaitingTimeStatLinkC().addSample(busStopAgent().getWaitingTimeLinkCSum()/environsAgent().getFansDeparted());
                
                fansDepartedLateLinkARepl.addSample(environsAgent().getPeopleLostStartMatchLinkA().mean());
                fansDepartedLateLinkBRepl.addSample(environsAgent().getPeopleLostStartMatchLinkB().mean());
                fansDepartedLateLinkCRepl.addSample(environsAgent().getPeopleLostStartMatchLinkC().mean());
                
                averageWaitingTimeRepl.addSample(busStopAgent().getWaitingTimeStat().mean());
                averageWaitingTimeLinkARepl.addSample(busStopAgent().getWaitingTimeStatLinkA().mean());
                averageWaitingTimeLinkBRepl.addSample(busStopAgent().getWaitingTimeStatLinkB().mean());
                averageWaitingTimeLinkCRepl.addSample(busStopAgent().getWaitingTimeStatLinkC().mean());
                
                peopleLostStartMatchRepl.addSample(environsAgent().getPeopleLostStartMatch().mean());
                fansFullTimeInSystem.addSample(environsAgent().getFansTimeInSystem().mean());
                
                for(int i = 0; i < busAgent().getBuses_A().size(); i++) {
                    Bus myBus = busAgent().getBuses_A().get(i);
                    double fullTimeSimulation = this.currentTime() - this.startSimulation - myBus.getStartTime();
                    double timeE = myBus.getFullTimeEmptyBus() / fullTimeSimulation;
                    busAgent().getEmptyBusesLinkATime().addSample(timeE);
                }
                
                for(int i = 0; i < busAgent().getBuses_B().size(); i++) {
                    Bus myBus = busAgent().getBuses_B().get(i);
                    double fullTimeSimulation = this.currentTime() - this.startSimulation - myBus.getStartTime();
                    double timeE = myBus.getFullTimeEmptyBus() / fullTimeSimulation;
                    busAgent().getEmptyBusesLinkBTime().addSample(timeE);
                }
                
                for(int i = 0; i < busAgent().getBuses_C().size(); i++) {
                    Bus myBus = busAgent().getBuses_C().get(i);
                    double fullTimeSimulation = this.currentTime() - this.startSimulation - myBus.getStartTime();
                    double timeE = myBus.getFullTimeEmptyBus() / fullTimeSimulation;
                    busAgent().getEmptyBusesLinkCTime().addSample(timeE);
                }
                
                emptyBusesLinkATimeRepl.addSample(busAgent().getEmptyBusesLinkATime().mean());
                emptyBusesLinkBTimeRepl.addSample(busAgent().getEmptyBusesLinkBTime().mean());
                emptyBusesLinkCTimeRepl.addSample(busAgent().getEmptyBusesLinkCTime().mean());
        }

	@Override
	public void simulationFinished()
	{
		// Dysplay simulation results
		super.simulationFinished();
/*
                try {
                //writeResultsFromScript();
                writeResultsFromScriptMicrobuses();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MySimulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MySimulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MySimulation.class.getName()).log(Level.SEVERE, null, ex);
            }*/
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		setModelAgent(new ModelAgent(Id.modelAgent, this, null));
		setBusAgent(new BusAgent(Id.busAgent, this, modelAgent()));
		setBusStopAgent(new BusStopAgent(Id.busStopAgent, this, modelAgent()));
		setEnvironsAgent(new EnvironsAgent(Id.environsAgent, this, modelAgent()));
	}

	private ModelAgent _modelAgent;

public ModelAgent modelAgent()
	{ return _modelAgent; }

	public void setModelAgent(ModelAgent modelAgent)
	{_modelAgent = modelAgent; }

	private BusAgent _busAgent;

public BusAgent busAgent()
	{ return _busAgent; }

	public void setBusAgent(BusAgent busAgent)
	{_busAgent = busAgent; }

	private BusStopAgent _busStopAgent;

public BusStopAgent busStopAgent()
	{ return _busStopAgent; }

	public void setBusStopAgent(BusStopAgent busStopAgent)
	{_busStopAgent = busStopAgent; }

	private EnvironsAgent _environsAgent;

public EnvironsAgent environsAgent()
	{ return _environsAgent; }

	public void setEnvironsAgent(EnvironsAgent environsAgent)
	{_environsAgent = environsAgent; }
	//meta! tag="end"


    public double getStartMatch() {
        return startMatch;
    }
    
    public void setStartMatch(double startMatch) {
        this.startMatch = startMatch;
    }

    public void calculateStartSimulation() {
        this.startSimulation = this.startMatch - (busStopAgent().getMaxSumDuration() + (75 * 60));
    }

    public double getStartSimulation() {
        return startSimulation;
    }

    public void setWaitingMode(boolean waitingMode) {
        this.waitingMode = waitingMode;
    }

    public boolean isWaitingMode() {
        return waitingMode;
    }

    public void setSimulationMode(boolean simulationMode) {
        this.simulationMode = simulationMode;
    }

    public boolean isSimulationMode() {
        return simulationMode;
    }

    public Stat getAverageProfitMicrobuses() {
        return averageProfitMicrobuses;
    }

    
    
    public Stat getAverageWaitingTimeRepl() {
        return averageWaitingTimeRepl;
    }

    public Stat getPeopleLostStartMatchRepl() {
        return peopleLostStartMatchRepl;
    }

    public Stat getFansFullTimeInSystem() {
        return fansFullTimeInSystem;
    }

    public Stat getEmptyBusesLinkATimeRepl() {
        return emptyBusesLinkATimeRepl;
    }

    public Stat getEmptyBusesLinkBTimeRepl() {
        return emptyBusesLinkBTimeRepl;
    }

    public Stat getEmptyBusesLinkCTimeRepl() {
        return emptyBusesLinkCTimeRepl;
    }

    
        public void writeResultsFromScriptMicrobuses() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        
        FileWriter fileWriter = new FileWriter("data.txt", true); //Set true for 
        PrintWriter writer = new PrintWriter(fileWriter);
        for(int i = 0; i < this.busAgent().getBuses_A().size(); i++) {
            Bus myBus = this.busAgent().getBuses_A().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        for(int i = 0; i < this.busAgent().getBuses_B().size(); i++) {
            Bus myBus = this.busAgent().getBuses_B().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        for(int i = 0; i < this.busAgent().getBuses_C().size(); i++) {
            Bus myBus = this.busAgent().getBuses_C().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        writer.println(";;;;;;;;Nestihli;" + Math.round((this.getPeopleLostStartMatchRepl().mean() * 100) * 100.0) / 100.0 + 
                ";CasCak;" + Math.round((this.getAverageWaitingTimeRepl().mean() / 60) * 100.0) / 100.0 + 
                ";ProfitMic;" + this.getAverageProfitMicrobuses().mean());
        writer.println("\n");
        writer.close();
    }
        
        public void writeResultsFromScript() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        
        FileWriter fileWriter = new FileWriter("data.txt", true); //Set true for 
        PrintWriter writer = new PrintWriter(fileWriter);
        for(int i = 0; i < this.busAgent().getBuses_A().size(); i++) {
            Bus myBus = this.busAgent().getBuses_A().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        for(int i = 0; i < this.busAgent().getBuses_B().size(); i++) {
            Bus myBus = this.busAgent().getBuses_B().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        for(int i = 0; i < this.busAgent().getBuses_C().size(); i++) {
            Bus myBus = this.busAgent().getBuses_C().get(i);
            writer.println("ID;" + myBus.getId() + ";Start BS;" + myBus.getIdStartStop() + ";StartTime;" + myBus.getStartTime());
        }
        
        writer.println(";;;;;;;;Nestihli;" + Math.round((this.getPeopleLostStartMatchRepl().mean() * 100) * 100.0) / 100.0 + 
                ";CasCak;" + Math.round((this.getAverageWaitingTimeRepl().mean() / 60) * 100.0) / 100.0 + 
                ";PeopleLostA;" + this.getFansDepartedLateLinkARepl().mean() * 100 + 
                ";PeopleLostB;" + this.getFansDepartedLateLinkBRepl().mean() * 100 + 
                ";PeopleLostC;" + this.getFansDepartedLateLinkCRepl().mean() * 100 +
                ";waitTimeA;" + this.getAverageWaitingTimeLinkARepl().mean() / 60 +
                ";waitTimeB;" + this.getAverageWaitingTimeLinkBRepl().mean() / 60 +
                ";waitTimeC;" + this.getAverageWaitingTimeLinkCRepl().mean() / 60);
        writer.println("\n");
        writer.close();
    }

    public Stat getFansDepartedLateLinkARepl() {
        return fansDepartedLateLinkARepl;
    }

    public Stat getFansDepartedLateLinkBRepl() {
        return fansDepartedLateLinkBRepl;
    }

    public Stat getFansDepartedLateLinkCRepl() {
        return fansDepartedLateLinkCRepl;
    }

    public Stat getAverageWaitingTimeLinkARepl() {
        return averageWaitingTimeLinkARepl;
    }

    public Stat getAverageWaitingTimeLinkBRepl() {
        return averageWaitingTimeLinkBRepl;
    }

    public Stat getAverageWaitingTimeLinkCRepl() {
        return averageWaitingTimeLinkCRepl;
    }
    
    
    
}
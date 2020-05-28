package simulation;

import OSPABA.*;

public class Mc extends IdList
{
	//meta! userInfo="Generated code: do not modify", tag="begin"
	public static final int requestResponseGetCurrBusStop = 1009;
	public static final int notice = 1001;
	public static final int requestResponse = 1004;
	public static final int requestResponseGetBusStops = 1011;
	//meta! tag="end"

	// 1..1000 range reserved for user
        public static final int init = 1;
        
        public static final int planningBusEnd = 10;
        
        public static final int ridingBus = 11;
        public static final int ridingBusEnd = 12;

        public static final int busStopService = 13;    
        public static final int busStopServiceEnd = 14; 
        
        public static final int startSimulate = 15; 
        public static final int waitingForStartArriveEnd = 16; 
        public static final int startArriving = 17; 
        public static final int customerArrived = 18; 
        public static final int customerBoarded = 19; 
        public static final int startBusPLanning = 20;
        public static final int startExit = 21;
        public static final int customerLeave = 22;
        public static final int allCustomersLeave = 23;
        public static final int busWaitingOnBusStopEnd = 24;
}
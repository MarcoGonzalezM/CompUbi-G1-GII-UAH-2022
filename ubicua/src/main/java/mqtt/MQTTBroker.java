package mqtt;

import java.util.Date;

public class MQTTBroker 
{		
	private static int qos = 1;
	private static String broker = "tcp://127.0.0.1:1883";
	private static String clientId = "uahlockers";
		
	public MQTTBroker()
	{
	}

	public static int getQos() {
            return qos;
	}

	public static String getBroker() {
            return broker;
	}

	public static String getClientId() {
            return (clientId + new Date().getTime());
	}			
}



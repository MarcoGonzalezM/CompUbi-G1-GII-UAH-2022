package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import database.ConectionDDBB;
import database.Topics;
import logic.Log;
import logic.Logic;

public class MQTTSuscriber implements MqttCallback
{
	public void searchTopicsToSuscribe(MQTTBroker broker){
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		ArrayList<String> topics = new ArrayList<>();
		try{
			con = conector.obtainConnection(true);
			Log.logmqtt.debug("Database Connected");
			
			//Get Cities to search the main topic
			PreparedStatement psTaquilleros = ConectionDDBB.GetTaquilleros(con);
			Log.logmqtt.debug("Query to search taquilleros=> {}", psTaquilleros.toString());
			ResultSet rsTaquilleros = psTaquilleros.executeQuery();
			while (rsTaquilleros.next()){
				topics.add("Taquilleros" + rsTaquilleros.getInt("ID")+"/#");
			}
			topics.add("test");
			suscribeTopic(broker, topics);			
		} 
		catch (NullPointerException e){Log.logmqtt.error("Error: {}", e);} 
		catch (Exception e){Log.logmqtt.error("Error:{}", e);} 
		finally{conector.closeConnection(con);}
	}
	
        
        
	public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics) {
            Log.logmqtt.debug("Suscribe to topics");
            MemoryPersistence persistence = new MemoryPersistence();
            try {
                MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
                sampleClient.connect(connOpts);
                Log.logmqtt.debug("Mqtt Connected");
                sampleClient.setCallback(this);
                for (int i = 0; i < topics.size(); i++) {
                    sampleClient.subscribe(topics.get(i));
                    Log.logmqtt.info("Subscribed to {}", topics.get(i));
                }

            } catch (MqttException me) {
                Log.logmqtt.error("Error suscribing topic: {}", me);
            } catch (Exception e) {
                Log.logmqtt.error("Error suscribing topic: {}", e);
            }
        }
	
        
        
        
	@Override
	public void connectionLost(Throwable cause) 
	{	
	}

        
        
        
        
        
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{
            Log.logmqtt.info("{}: {}", topic, message.toString());
            String[] topics = topic.split("/");
            Topics newTopic = new Topics();
            newTopic.setValue(message.toString());
            if (topic.contains("hay_paquete")) {
                newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                newTopic.setIdTaquilla(topics[1].replace("Taquilla", ""));
                newTopic.setHay_paquete(topics[2].replace("Hay_paquete", ""));
                Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}, Hay_paquete{}: {}",
                        newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), newTopic.getHay_paquete(), message.toString());

                //Store the information of the sensor
                Logic.storeNewMeasurement(newTopic);
            } else if (topic.contains("abrir")) {
                newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                newTopic.setIdTaquilla(topics[1].replace("Taquilla", ""));
                newTopic.setAbrir(topics[2].replace("Abrir", ""));
                Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}, Abrir{}: {}",
                        newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), newTopic.getAbrir(), message.toString());

            } else if (topic.contains("estado")) {
                newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                newTopic.setIdTaquilla(topics[1].replace("Taquilla", ""));
                newTopic.setEstado(topics[2].replace("Estado", ""));
                Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}, Estado{}: {}",
                        newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), newTopic.getEstado(), message.toString());
            }
            
            
            else {
                if (topic.contains("clave")) {
                    newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                    newTopic.setClave(topics[1].replace("Clave", ""));
                    Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}: {}",
                            newTopic.getIdTaquillero(), newTopic.getClave(), message.toString());
                    Logic.comprobarClave(newTopic);

                    
                } else if(topic.contains("taquilla")){
                    newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                    newTopic.setIdTaquilla(topics[1].replace("Taquilla", ""));
                    Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}: {}",
                            newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), message.toString());

                }
                else {
                    if (topic.contains("taquillero")) {
                        newTopic.setIdTaquillero(topics[0].replace("Taquillero", ""));
                        Log.logmqtt.info("Mensaje from taquillero{}: {}",
                                newTopic.getIdTaquillero(), message.toString());
                    } else {

                    }
                }
            }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) 
	{		
	}
}


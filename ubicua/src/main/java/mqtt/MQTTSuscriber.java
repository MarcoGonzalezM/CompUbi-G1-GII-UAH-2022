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
            PreparedStatement psTaquilleros = ConectionDDBB.GetTaquillasFromTaquillero(con);
            Log.logmqtt.debug("Query to search taquilleros=> {}", psTaquilleros.toString());
            ResultSet rsTaquilleros = psTaquilleros.executeQuery();
            
            //Se suscribe a todos los taqulleros EX: Taquillero1/#
            while (rsTaquilleros.next()){
                    topics.add("Taquillero" + rsTaquilleros.getInt("ID")+"/#");
            }
            
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
        if (topic.contains("hay_paquete"))
        {
            newTopic.setIdTaquillero(Integer.parseInt(topics[0].replace("Taquillero", "")));
            newTopic.setIdTaquilla(Integer.parseInt(topics[1].replace("Taquilla", "")));
            newTopic.setHay_paquete(topics[2]);

            if(newTopic.getValue().equals("Si"))
            {
                Logic.updateOcupadoTaquilla(newTopic.getIdTaquillero(), newTopic.getIdTaquilla() , true);
            }
            else if(newTopic.getValue().equals("No"))
            {
                Logic.updateOcupadoTaquilla(newTopic.getIdTaquillero(), newTopic.getIdTaquilla() , false);
            }

            Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}, Hay_paquete{}: {}",
                    newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), newTopic.getHay_paquete(), message.toString());

        }
        else if (topic.contains("estado"))
        {
            newTopic.setIdTaquillero(Integer.parseInt(topics[0].replace("Taquillero", "")));
            newTopic.setIdTaquilla(Integer.parseInt(topics[1].replace("Taquilla", "")));
            newTopic.setEstado(topics[2]);

            if(newTopic.getValue().equals("Abierto"))
            {
                Logic.updateEstadoTaquilla(newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), 0);
            }
            else if(newTopic.getValue().equals("Cerrado"))
            {
                Logic.updateEstadoTaquilla(newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), 2);
            }
            else if(newTopic.getValue().equals("Autenticando"))
            {
                Logic.updateEstadoTaquilla(newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), 1);
            }

            Log.logmqtt.info("Mensaje from Taquillero{}, Taquilla{}, Estado{}: {}",
                    newTopic.getIdTaquillero(), newTopic.getIdTaquilla(), newTopic.getEstado(), message.toString());
        }
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) 
    {		
    }
}


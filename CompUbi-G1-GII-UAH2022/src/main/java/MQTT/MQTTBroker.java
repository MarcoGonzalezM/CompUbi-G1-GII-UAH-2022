/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MQTT;

/**
 *
 * @author marco
 */
public class MQTTBroker {
    private static int qos = 2; // Calidad del servicio, a menor número menor calidad
                                // 0 = Fire and Forget
                                // 1 = At least once (Hasta recibir el siguiente paquete en la cola)
                                // 2 = Once (Hasta recibir un ACK)
    private static String broker = "";
    private String clientID = "";

    public MQTTBroker() {
    }
    
    public static int getQos() {
        return qos;
    }

    public static String getBroker() {
        return broker;
    }

    public String getClientID() {
        return clientID;
    }
}

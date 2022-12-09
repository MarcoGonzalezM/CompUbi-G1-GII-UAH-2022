package logic;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;
import mqtt.MQTTPublisher;

/**
 *	ES: Clase encargada de inicializar el sistema y de lanzar el hilo de previsi�n meteorol�gica
 *	EN: Class in charge of initializing the thread of weather forecast
 */
@WebListener
public class ProjectInitializer implements ServletContextListener 
{
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
    }

    @Override
    /**
     *	ES: Metodo empleado para detectar la inicializacion del servidor	<br>
     * 	EN: Method used to detect server initialization
     * 	@param sce <br>
     * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
     * 	EN: Context event created during server launch
     */
    public void contextInitialized(ServletContextEvent sce)
    {
        Log.log.info("-->Suscribe Topics<--");
        MQTTBroker broker = new MQTTBroker();
        MQTTSuscriber suscriber = new MQTTSuscriber(broker);
        suscriber.searchTopicsToSuscribe(broker);
        MQTTPublisher.publish(broker, "test/hola", "estoy");

    }	
}
import mqttSubscriber
import postgresConnection

if __name__ == '__main__':
    pgConn = postgresConnection.PostgresConnection()

    pgConn.connectToPostgres()
    datos = pgConn.obtenerDatos()

    subscriber = mqttSubscriber.MqttSubscriber("localhost", 1883, "mqttListenner")
    subscriber.run(datos)

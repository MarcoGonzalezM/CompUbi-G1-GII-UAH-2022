import paho.mqtt.client as mqtt
import requests

class MqttSubscriber:
    def __init__(self, broker_address, port, clientId):
        self.broker_address = broker_address
        self.port = port
        self.clientId = clientId

    def connect_mqtt(self):
        def on_connect(client, userdata, flags, rc):
            if rc == 0:
                print("Connected to MQTT Broker!")
            else:
                print("Failed to connect, return code %d\n", rc)

        self.client = mqtt.Client(self.clientId)
        self.client.on_connect = on_connect
        self.client.connect(self.broker_address, self.port)

    def subscribe(self, topics):
        def on_message(client, userdata, msg):
            mensaje = str(msg.payload.decode("utf-8"))
            topic = str(msg.topic)

            print("Mensaje recibido: ", mensaje)
            print("Topic: ", topic)
            requests.get("http://localhost:8080/uahlockers/suscripcionesMqtt?topic=" + topic + "&mensaje=" + mensaje)

        for topic in topics:
            self.client.subscribe(topic + "/#")
            print("Subscribed to topic: ", topic + "/#")
            
        self.client.on_message = on_message

    def run(self, topics):
        self.connect_mqtt()
        self.subscribe(topics)
        self.client.loop_forever()
        
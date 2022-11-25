#include "Taquilla.h"

Taquilla::Taquilla(int ledRojo, int ledVerde, int ledAzul, int servo, int sensorTrig, int sensorEcho, int ledPaquete, PubSubClient client, int id)
{
    SR04 temp = SR04(sensorEcho, sensorTrig);
    this->sensor = temp;

    //ID
    this->id = id;

    //LED RGB
    this->ledRojo = ledRojo;
    this->ledVerde = ledVerde;
    this->ledAzul = ledAzul;
    digitalWrite(ledRojo, LOW);
    digitalWrite(ledVerde, HIGH);
    digitalWrite(ledAzul, LOW);

    //Servomotor
    this->servo.attach(servo);
    this->servo.write(0);

    //LED paquete
    this->ledPaquete = ledPaquete;
    digitalWrite(ledPaquete, LOW);

    //Cliente MQTT
    this->client = client;
}

void Taquilla::abrir()
{
    this->servo.write(0);

    digitalWrite(this->ledRojo, LOW);
    digitalWrite(this->ledVerde, HIGH);
    digitalWrite(this->ledAzul, LOW);

    String topic = "Taquillero/Taquilla" + String(id) + "/estado";
    char buff[topic.length()];
    topic.toCharArray(buff, topic.length());

    client.publish(buff, "Abierto");
    
}

void Taquilla::cerrar()
{
    this->servo.write(90);

    digitalWrite(this->ledRojo, HIGH);
    digitalWrite(this->ledVerde, LOW);
    digitalWrite(this->ledAzul, LOW);

    String topic = "Taquillero/Taquilla" + String(id) + "/estado";
    char buff[topic.length()];
    topic.toCharArray(buff, topic.length());

    client.publish(buff, "Cerrado");
}

void Taquilla::estaPaquete()
{
    int distancia = this->sensor.Distance();

    String topic = "Taquillero/Taquilla" + String(id) + "/paquete";
    char buff[topic.length()];
    topic.toCharArray(buff, topic.length());

    if(distancia < 20)
    {
        digitalWrite(this->ledPaquete, HIGH);
        
        client.publish(buff, "Si");
    }
    else
    {
        digitalWrite(this->ledPaquete, LOW);
        client.publish(buff, "No");
    }
}

void Taquilla::autenticar()
{
    digitalWrite(this->ledRojo, LOW);
    digitalWrite(this->ledVerde, LOW);
    digitalWrite(this->ledAzul, HIGH);
}
#include "Taquilla.h"
#include <ESP32Servo.h>
#include <SR04.h>
#include <PubSubClient.h>

Taquilla::Taquilla(int ledRojo, int ledVerde, int ledAzul, int servo, int sensorTrig, int sensorEcho, int ledPaquete, PubSubClient client, int id)
{
    //ID
    this->id = id;

    //LED RGB
    this->ledRojo = ledRojo;
    this->ledVerde = ledVerde;
    this->ledAzul = ledAzul;
    digitalWrite(ledRojo, LOW);
    digitalWrite(ledVerde, HIGH);
    digitalWrite(ledAzul, LOW);

    //Sensor de ultrasonido
    this->sensor = SR04(sensorEcho,sensorTrig);

    //Servomotor
    this->servo.attach(servo);
    this->servo.write(0);

    //LED paquete
    this->ledPaquete = ledPaquete;
    digitalWrite(ledPaquete, LOW);

    //Cliente MQTT
    this->client = client;
};

void Taquilla::abrir()
{
    this->servo.write(0);

    digitalWrite(this->ledRojo, LOW);
    digitalWrite(this->ledVerde, HIGH);
    digitalWrite(this->ledAzul, LOW);

    client.publish("Taquilla" + (String) id + "/estado", "Abierto");
    
};

void Taquilla::cerrar()
{
    this->servo.write(90);

    digitalWrite(this->ledRojo, HIGH);
    digitalWrite(this->ledVerde, LOW);
    digitalWrite(this->ledAzul, LOW);

    client.publish("Taquilla" + (String) id + "/estado", "Cerrado");
};

void Taquilla::autenticar()
{
    digitalWrite(this->ledRojo, LOW);
    digitalWrite(this->ledVerde, LOW);
    digitalWrite(this->ledAzul, HIGH);
};

void Taquilla::estaPaquete()
{
    int distancia = this->sensor.Distance();
    String idStr = (String) this->id;

    if(distancia < 20)
    {
        digitalWrite(this->ledPaquete, HIGH);
        
        client.publish("Taquilla" + idStr + "/paquete", "Si");
    }
    else
    {
        digitalWrite(this->ledPaquete, LOW);
        client.publish("Taquilla" + idStr + "/paquete", "No");
    }
};

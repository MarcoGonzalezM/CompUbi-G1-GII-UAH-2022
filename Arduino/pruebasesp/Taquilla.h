#include <ESP32Servo.h>
#include <SR04.h>
#include <PubSubClient.h>

class Taquilla
{
  private:
    int id;

    int ledRojo;
    int ledVerde;
    int ledAzul;

    int ledPaquete;
    Servo servo ;
    SR04 sensor = SR04(1, 2);

    PubSubClient client;

  public:
    Taquilla(int ledRojo, int ledVerde, int ledAzul, int servo, int sensorTrig, int sensorEcho, int ledPaquete, PubSubClient client, int id);

    void abrir();
    void cerrar();
    void estaPaquete();
    void autenticar();

};
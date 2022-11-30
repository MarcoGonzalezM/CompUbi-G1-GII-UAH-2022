#include <ESP32Servo.h>
#include <SR04.h>
#include <PubSubClient.h>

class Taquilla
{
  private:
    int id;

    int ledRojo;
    int ledVerde;

    int ledPaquete;
    Servo servo;

  public:
    Taquilla(int ledRojo, int ledVerde, int servo, int ledPaquete, int id);

    void abrir();
    void cerrar();
    bool estaPaquete(SR04 sensor);
    void autenticar();

    String getId();

};
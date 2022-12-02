#include "Taquilla.h"

Taquilla::Taquilla(int ledRojo, int ledVerde, int servo, int ledPaquete, int id)
{
  //ID
  this->id = id;

  //LED RGB
  this->ledRojo = ledRojo;
  this->ledVerde = ledVerde;
  digitalWrite(ledRojo, LOW);
  digitalWrite(ledVerde, HIGH);

  //Servomotor
  this->servo.attach(servo);
  this->servo.write(180);

  //LED paquete
  this->ledPaquete = ledPaquete;
  digitalWrite(ledPaquete, LOW);
}

void Taquilla::abrir()
{
  this->servo.write(180);

  digitalWrite(this->ledRojo, LOW);
  digitalWrite(this->ledVerde, HIGH);
}

void Taquilla::cerrar()
{
  this->servo.write(90);

  digitalWrite(this->ledRojo, HIGH);
  digitalWrite(this->ledVerde, LOW);
}

bool Taquilla::estaPaquete(SR04 sensor)
{
  int distancia = sensor.Distance();
  bool estar = true;

  if(distancia < 9)
  {
    digitalWrite(this->ledPaquete, HIGH);
    Serial.println("Si paquete");
  }
  else
  {
    digitalWrite(this->ledPaquete, LOW);
    estar = false;
    Serial.println("No paquete");
  }

  return estar;
}

void Taquilla::autenticar()
{
  digitalWrite(this->ledRojo, HIGH);
  digitalWrite(this->ledVerde, HIGH);
}

String Taquilla::getId()
{
  return String(this->id);
}
#include "SR04.h"
#include <Servo.h>

//PINES
//Sensor Ultrasonido
int usTrig1 = 12;
int usEcho1 = 11;

//Led RGB
int rgbRojo1 = 6;
int rgbAzul1 = 3;
int rgbVerde1 = 5;

//Servomotor
int pinservo1 = 9;

//VARIABLES
//Sensor Ultrasonido
SR04 sr04 = SR04(usEcho1,usTrig1);
long distancia;

//Servomotor
Servo servo1;

void setup()
{
  pinMode(rgbRojo1, OUTPUT);
  pinMode(rgbAzul1, OUTPUT);
  pinMode(rgbVerde1, OUTPUT);

  servo1.attach(pinservo1);
  servo1.write(0);

  Serial.begin(9600);
  delay(1000);
}

void ledRojo(int pinRojo, int pinVerde, int pinAzul)
{
  digitalWrite(pinRojo, HIGH);
  digitalWrite(pinAzul, LOW);
  digitalWrite(pinVerde, LOW);
}

void ledVerde(int pinRojo, int pinVerde, int pinAzul)
{
  digitalWrite(pinRojo, LOW);
  digitalWrite(pinAzul, LOW);
  digitalWrite(pinVerde, HIGH);
}

void ledAmarillo(int pinRojo, int pinVerde, int pinAzul)
{
  digitalWrite(pinRojo, HIGH);
  digitalWrite(pinAzul, LOW);
  digitalWrite(pinVerde, HIGH);
}

void abrirTaquilla(Servo servo)
{
  servo.write(0);
}

void cerrarTaquilla(Servo servo)
{
  servo.write(90);
}

void loop() 
{
  distancia=sr04.Distance();

  if(distancia>20)
  {
    ledRojo(rgbRojo1, rgbVerde1, rgbAzul1);
    abrirTaquilla(servo1);
  }
  else
  {
    ledAmarillo(rgbRojo1, rgbVerde1, rgbAzul1);
    cerrarTaquilla(servo1);
  }

  delay(500);
}

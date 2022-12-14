#include <WiFi.h>
#include <SPIFFS.h>
#include <PubSubClient.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <ESP32Servo.h>
#include <SR04.h>
#include <Keypad_I2C.h>
#include <Keypad.h>
#include "Taquilla.h"

//CONSTANTES
//wifi
const char* ssid = "UAHLockers";
const char* password = "estoesesparta";

//MQTT servidor
const char* mqtt_server = "192.168.0.166";
const int mqtt_port = 1883;

//PADNUMERICO
const byte ROWS = 4;
const byte COLS = 4;

char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

byte rowPins[ROWS] = {0, 1, 2, 3};
byte colPins[COLS] = {4, 5, 6, 7};

Keypad_I2C customKeypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS, 0x20);

//LCD
LiquidCrystal_I2C lcd(0x27,16,2);

//Wifi
WiFiClient espClient;

//Cliente MQTT
PubSubClient client(espClient);

//TAQUILLERO1
//leds
const int ledRojo1 = 5;
const int ledVerde1 = 18;
const int ledPaquete1 = 17;

//Servomotor
const int pinServo1 = 12;

//Sensor Ultrasonido
const int usTrig1 = 33;
const int usEcho1 = 32;

//NUMERO DE TAQUILLAS
const int numTaquillas = 1;

//IDENTIFICADOR TAQUILLERO
const int idTaquillero = 1;
String nombreTaquillero = "Taquillero" + String(idTaquillero);

//Sensores Ultrasonido
SR04 sensores[] =
{
  SR04(usEcho1, usTrig1)
};

//Taquillas
Taquilla taquillas[] = 
{
  Taquilla(ledRojo1, ledVerde1, pinServo1, ledPaquete1, 1)
};

//Clave a introducir
String clave = "";

void setup()
{
  Serial.begin(115200);

  setup_wifi();

  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);

  pinMode(ledRojo1, OUTPUT);
  pinMode(ledVerde1, OUTPUT);
  pinMode(ledPaquete1, OUTPUT);

  lcd.init();
  lcd.backlight();
  lcd.print("Escriba en PAD:");

  Serial.println("Conectado");

  customKeypad.begin();
}

void setup_wifi()
{
  Serial.println("Conectando al wifi");

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
}

void callback(char* topic, byte* message, unsigned int length)
{
  String msgTemp;

  for(int i = 0; i<length; i++)
  {
    msgTemp += (char)message[i];
  }
  Serial.println(msgTemp);
  Serial.println(topic);

  String strTopic = String(topic);
  
  for(int i = 0; i < numTaquillas; i++)
  {
    if(strTopic == nombreTaquillero + "/Taquilla" + taquillas[i].getId() + "/accion")
    {
      String topic = nombreTaquillero + "/Taquilla" + taquillas[i].getId() +  + "/estado";
      char buff[topic.length()+1];
      topic.toCharArray(buff, topic.length()+1);

      if(msgTemp == "Abrir")
      {
        taquillas[i].abrir();
        client.publish(buff, "Abierto");
      }
      if(msgTemp == "Cerrar")
      {
        taquillas[i].cerrar();
        client.publish(buff, "Cerrado");
      }
      if(msgTemp == "Autenticar")
      {
        taquillas[i].autenticar();
        client.publish(buff, "Cerrado");
      }
      if(msgTemp == "Estapaquete")
      {
        String topicpaquete = nombreTaquillero + "/Taquilla" + taquillas[i].getId() +  + "/hay_paquete";
        char buffp[topicpaquete.length()+1];
        topicpaquete.toCharArray(buffp, topicpaquete.length()+1);

        if(taquillas[i].getEstado())
        {
          client.publish(buffp, "Si");
        }
        else
        {
          client.publish(buffp, "No");
        }
      }
    } 
  }

}

void reconnect()
{
  char buff[nombreTaquillero.length()+1];
  nombreTaquillero.toCharArray(buff, nombreTaquillero.length()+1);

  while(!client.connected())
  {
    if(client.connect(buff))
    {
      String topic = nombreTaquillero + "/#";
      char buff2[topic.length()+1];
      topic.toCharArray(buff2, topic.length()+1);

      client.subscribe(buff2);
    }
    else
    {
      delay(5000);
    }
  }
}

void resetDisplay()
{
  lcd.clear();
  lcd.print("Escriba en PAD:");
}

void loop()
{
  //Intenta conectar al servidor mqtt
  if(!client.connected())
  {
    reconnect();
  }
  client.loop();

  //obtiene el caracter del keypad  
  char customKey = customKeypad.getKey();
  if(clave.length() < 16) //Si la clave es menor que la longitud del display
  {
    if((customKey == '*') && (clave.length() != 0)) //Si el caracter es * y la longitud de la clave es vacia
    {
      clave = clave.substring(0, clave.length()-1); //Elimina el ultimo caracter
      resetDisplay();                               //Resetea el display
    }
    else if(customKey == '#')                       //Si el caracter es #
    {
      //La clave la convertimos en un char array
      int longitudClave = clave.length() + 1;
      char arrayClave[longitudClave];
      clave.toCharArray(arrayClave, longitudClave); 
      
      String topicClave = nombreTaquillero + "/clave";
      char buffClave[topicClave.length()+1];
      topicClave.toCharArray(buffClave, topicClave.length()+1);

      //Se publica la clave en el broker con el topic
      client.publish(buffClave, arrayClave);
      
      //Se resetea la clave y el display
      clave = "";
      resetDisplay();

    }
    else if(customKey) // Si se lee un caracter en el pad numerico
    {
      clave = clave + customKey;  //annadimos el caracter a la clave
    }
  }
  else
  {
    //Si se pasa de la longitud, reseteamos el display y la clave
    clave = ""; 
    resetDisplay();
  }

  //Imprimimos la clave por el display
  lcd.setCursor(0,1);
  lcd.print(clave);

  for(int i = 0; i < numTaquillas; i++)
  {
    taquillas[i].estaPaquete(sensores[i]);
  }

  delay(200);

}

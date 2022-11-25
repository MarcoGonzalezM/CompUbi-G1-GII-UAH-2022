#include <WiFi.h>
#include <SPIFFS.h>
#include <PubSubClient.h>
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>
#include <ESP32Servo.h>
#include <SR04.h>

#include "Taquilla.h"

//CONSTANTES
//wifi
const char* ssid = "MIWIFI_2G_REfs";
const char* password = "PpejR6Ku";

//MQTT servidor
const char* mqtt_server = "192.168.1.141";
const int mqtt_port = 1883;

//PINES
//leds
const int ledRojo1 = 13;
const int ledVerde1 = 12;
const int ledAzul1 = 14;

const int ledPaquete1 = 35;

//PADNUMERICO
const byte ROWS = 4;
const byte COLS = 4;

//Sensor Ultrasonido
const int usTrig1 = 27;
const int usEcho1 = 14;

//Pad Numerico
char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

byte rowPins[ROWS] = {15, 2, 0, 4};
byte colPins[COLS] = {16, 17, 5, 18};


//VARIABLES GLOBALES
//Servomotor
const int pinServo1 = 12;

//Sensor Ultrasonido
SR04 sr04 = SR04(usEcho1,usTrig1);
long distancia;

//Wifi
WiFiClient espClient;

//Cliente MQTT
PubSubClient client(espClient);

//Pad Numerico
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

//LCD
LiquidCrystal_I2C lcd(0x27,16,2);

//Clave a introducir
String clave = "";

Taquilla taquillas[] =
  {
    Taquilla(ledRojo1, ledVerde1, ledAzul1, pinServo1, usTrig1, usEcho1, ledPaquete1, client, 0)
  };

void setup()
{
  Serial.begin(115200);

  setup_wifi();

  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);

  lcd.init();
  lcd.backlight();
  lcd.print("Escriba en PAD:");
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


  for(int i = 0; i < sizeof(taquillas); i++)
  {
    String idStr = (String) i;
    if(String(topic) == "Taquillero1/Taquilla" + idStr + "/abrir")
    {
      if(msgTemp == "Abrir")
      {
        taquillas[i].abrir();
      }
      else if(msgTemp == "Cerrar")
      {
        taquillas[i].cerrar();
      }
    }
  }
  
}

void reconnect()
{
  while(!client.connected())
  {
    if(client.connect("Taquillero1"))
    {
      client.subscribe("Taquillero1");
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
      
      //Se publica la clave en el broker con el topic
      client.publish("Taquillero1/clave", arrayClave);
      
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

  //Recogemos la distancia del sensor ultrasonido
  for(int i = 0; i < sizeof(taquillas); i++)
  {
    taquillas[i].estaPaquete();
  }

  delay(100);

}

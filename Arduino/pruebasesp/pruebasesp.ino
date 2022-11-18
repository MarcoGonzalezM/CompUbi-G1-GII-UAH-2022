#include <WiFi.h>
#include <SPIFFS.h>
#include <PubSubClient.h>
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>
#include <ESP32Servo.h>
#include <SR04.h>

//wifi
const char* ssid = "OnePlus 9R";
const char* password = "c65e6rv6";

//MQTT servidor
const char* mqtt_server = "192.168.154.15";
const int mqtt_port = 1883;

WiFiClient espClient;
PubSubClient client(espClient);

//leds
const int led1 = 13;
const int led2 = 35;

//PADNUMERICO
const byte ROWS = 4;
const byte COLS = 4;

//Servomotor
Servo servo1;


//Sensor Ultrasonido
int usTrig1 = 27;
int usEcho1 = 14;
SR04 sr04 = SR04(usEcho1,usTrig1);
long distancia;

char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

byte rowPins[ROWS] = {15, 2, 0, 4};
byte colPins[COLS] = {16, 17, 5, 18};
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

//LCD
LiquidCrystal_I2C lcd(0x27,16,2);

//Variables globales
String clave = "";
char customKey;

void setup()
{
  Serial.begin(115200);

  setup_wifi();

  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);

  servo1.attach(12);
  servo1.write(0);

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
  Serial.print("Mensaje recibido en el topic: ");
  Serial.print(topic);
  Serial.print(". mensaje: ");

  String msgTemp;

  for(int i = 0; i<length; i++)
  {
    msgTemp += (char)message[i];
  }
  Serial.print(msgTemp);
  Serial.println();

  if(String(topic) == "test/hola")
  {
    if(msgTemp == "Abrir")
    {
      Serial.println("Abierto");
      digitalWrite(led1, HIGH);
      servo1.write(0);
      client.publish("test/estoy", "Abierto");
    }
    else if(msgTemp == "Cerrar")
    {
      Serial.println("Cerrado");
      digitalWrite(led1, LOW);
      servo1.write(90);
      client.publish("test/estoy", "Cerrado");
    }
  }
}

void reconnect()
{
  while(!client.connected())
  {
    if(client.connect("ESP32Client"))
    {
      client.subscribe("test/hola");
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
  if(!client.connected())
  {
    reconnect();
  }
  client.loop();

  customKey = customKeypad.getKey();
  if(clave.length() < 16)
  {
    if((customKey == '*') && (clave.length() != 0))
    {
      clave = clave.substring(0, clave.length()-1);
      resetDisplay();
    }
    else if(customKey == '#')
    {
      int longitudClave = clave.length() + 1;
      char arrayClave[longitudClave];
      clave.toCharArray(arrayClave, longitudClave);
            
      client.publish("test/clave", arrayClave);
      
      clave = "";
      resetDisplay();

    }
    else if(customKey)
    {
      clave = clave + customKey;
    }
  }
  else
  {
    clave = "";
    resetDisplay();
  }

  lcd.setCursor(0,1);
  lcd.print(clave);


  distancia=sr04.Distance();
  if(distancia < 20)
  {
    client.publish("test/sensor", "Esta");
    digitalWrite(led2, HIGH);
  }
  else
  {
    client.publish("test/sensor", "No esta");
    digitalWrite(led2, LOW);
  }

  delay(100);

}

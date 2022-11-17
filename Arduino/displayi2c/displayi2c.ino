#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>

const byte ROWS = 4;
const byte COLS = 4;

char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

byte rowPins[ROWS] = {15, 2, 0, 4};
byte colPins[COLS] = {16, 17, 5, 18};

LiquidCrystal_I2C lcd(0x27,16,2);
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

void setup()
{
  lcd.init();
  lcd.backlight();
  lcd.print("Escriba en PAD:");
}

char customKey;
String cadena = "";

void loop()
{
  customKey = customKeypad.getKey();
  if(cadena.length() < 16)
  {
    if((customKey == '*') && (cadena.length() != 0))
    {
      cadena = cadena.substring(0, cadena.length()-1);
      lcd.clear();
      lcd.print("Escriba en PAD:");
    }
    else if(customKey)
    {
      cadena = cadena + customKey;
    }
  }
  else
  {
    cadena = "";
  }
  lcd.setCursor(0,1);
  lcd.print(cadena);

  delay(100);
  
}
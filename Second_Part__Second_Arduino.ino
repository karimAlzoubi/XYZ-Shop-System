#include <Servo.h>
#include <SharpIR.h>

// Define the servo objects with shorter names
Servo servoP;   // Servo for plastic
Servo servoPa;  // Servo for paper
Servo servoRoof; // Servo for roof

// Define the pins
const int irSensorPin = A0;
const int servoPPin = 3;     // Pin for plastic servo
const int servoPaPin = 4;    // Pin for paper servo
const int servoRoofPin = 5;  // Pin for roof servo
const int trigPin = 32;      // Trigger pin for ultrasonic sensor
const int echoPin = 33;      // Echo pin for ultrasonic sensor

// Define the initial angles
int plasticOpenAngle = 90;
int plasticCloseAngle = 170;
int paperOpenAngle = 90;
int paperCloseAngle = 33;

int roofOpenAngle = 120;   // Variable for roof servo open angle
int roofCloseAngle = 0;    // Variable for roof servo close angle

// Define the SharpIR sensor
#define model 20150
SharpIR SharpIR(irSensorPin, model);

int servoSpeed = 15;  // Speed for servo movement

void setup() {
  // Attach the servos to their respective pins
  servoP.attach(servoPPin);
  servoPa.attach(servoPaPin);
  servoRoof.attach(servoRoofPin);

  // Initialize the servos to the closed position
  moveServoSlowly(servoP, 90, plasticCloseAngle, servoSpeed);
  moveServoSlowly(servoPa, 90, paperCloseAngle, servoSpeed);
  moveServoSlowly(servoRoof, 90, roofCloseAngle, servoSpeed);

  // Initialize serial communication
  Serial.begin(9600);

  // Initialize ultrasonic sensor pins
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}

void loop() {
  // Check for serial input to control servos
  if (Serial.available() > 0) {
    char input = Serial.read();
    
    if (input == '1') {
      // Open and close plastic servo
      Serial.println("Opening plastic servo...");
      moveServoSlowly(servoP, plasticCloseAngle, plasticOpenAngle, servoSpeed);
      delay(2000);  // Keep plastic servo open for 2 seconds
      Serial.println("Closing plastic servo...");
      moveServoSlowly(servoP, plasticOpenAngle, plasticCloseAngle, servoSpeed);
    } 
    else if (input == '2') {
      // Open and close paper servo
      Serial.println("Opening paper servo...");
      moveServoSlowly(servoPa, paperCloseAngle, paperOpenAngle, servoSpeed);
      delay(2000);  // Keep paper servo open for 2 seconds
      Serial.println("Closing paper servo...");
      moveServoSlowly(servoPa, paperOpenAngle, paperCloseAngle, servoSpeed);
    } 
    else if (input == 's') {
      // Reset both servos to 90 degrees
      Serial.println("Stopping servos and resetting to 90 degrees...");
      moveServoSlowly(servoP, servoP.read(), 90, servoSpeed);
      moveServoSlowly(servoPa, servoPa.read(), 90, servoSpeed);
      
      // Halt the system in an infinite loop
      while (true) {
        // Infinite loop to halt the system
      }
    }
  }

  delay(100); // Small delay to avoid overwhelming serial input

  // Remove IR sensor printing for ultrasonic-only output
  // Read the ultrasonic sensor
  long duration, distance;
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  // Read the echo pin and calculate the distance
  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;  // Convert time to distance (cm)

  // Print the ultrasonic sensor distance
  Serial.print("Ultrasonic Distance: ");
  Serial.println(distance);

  if (distance <= 30) {
    Serial.println("Object detected within 30 cm, opening roof servo...");
    moveServoSlowly(servoRoof, roofCloseAngle, roofOpenAngle, servoSpeed);
    delay(3500);  // Keep roof servo open for 2 seconds
    Serial.println("Closing roof servo...");
    moveServoSlowly(servoRoof, roofOpenAngle, roofCloseAngle, servoSpeed);
  }

  delay(300);
}

// Function to move the servo slowly between two angles with speed control
void moveServoSlowly(Servo &servo, int startAngle, int endAngle, int speed) {
  int stepSize = 1;   // Angle change per step

  if (startAngle < endAngle) {
    for (int pos = startAngle; pos <= endAngle; pos += stepSize) {
      servo.write(pos);
      delay(speed);  // Use the speed variable to control the delay between each step
    }
  } else {
    for (int pos = startAngle; pos >= endAngle; pos -= stepSize) {
      servo.write(pos);
      delay(speed);  // Use the speed variable to control the delay between each step
    }
  }
}

#include <Servo.h>

// Servo objects
Servo servo1Left;   // Metal, left motor
Servo servo1Right;  // Metal, right motor
Servo servo2Left;  // No metal, left motor
Servo servo2Right; // No metal, right motor

// Define servo pins
#define servo1LeftPin 5  // Metal plate, left motor -- green
#define servo1RightPin 6 // Metal plate, right motor -- yellow
#define servo2LeftPin 10 // No metal plate, left motor -- green
#define servo2RightPin 9 // No metal plate, right motor -- yellow

// Pin definitions for sensors
#define metalSensorPin 11
#define trigPin 7  // Ultrasonic sensor trigger pin -- yellow
#define echoPin 8  // Ultrasonic sensor echo pin -- green

// Variables to store the current position of servos
int servo1LeftPos = 90;
int servo1RightPos = 90;
int servo2LeftPos = 90;
int servo2RightPos = 90;

// Base speed and calibration factors
const int baseSpeed = 15;  // Base movement speed

const int closeAngleNoMetal = 170;  // Closing angle for no metal plate
const int closeAngleMetal = 170;  // Closing angle for metal plate

const float noMetalPlateLeftFactor = 1.0;
const float noMetalPlateRightFactor = 1.0;
const float metalPlateLeftFactor = 1.0;
const float metalPlateRightFactor = 1.0;

void setup() {
  Serial.begin(9600);
  
  // Attach the servos to their respective pins
  servo1Left.attach(servo1LeftPin);
  servo1Right.attach(servo1RightPin);
  servo2Left.attach(servo2LeftPin);
  servo2Right.attach(servo2RightPin);

  // Setup metallic sensor pin
  pinMode(metalSensorPin, INPUT); // Digital input for metal sensor

  // Setup ultrasonic sensor pins
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  Serial.println("System ready.");
}

void loop() {
  resetServos();  // Close the panels to their respective close angles
  
  while (true) {
    // Check for 's' in serial monitor to reset to start position and stop operations
    if (Serial.available() > 0) {
      char command = Serial.read();
      if (command == 's') {
        returnToStart();
        Serial.println("System stopped.");
        while (true);  // Stop all operations
      }
    }

    // Check distance from ultrasonic sensor
    int distance = readUltrasonicDistance();
    Serial.print("Distance: ");
    Serial.println(distance);

    if (distance <= 30) {  // If distance is within 30 cm
      delay(5000);  // Wait for 5 seconds

      if (isMetalDetected()) {  // Check if the metal sensor detects metal
        openPanel(true);  // Open metal panel
      } else {
        openPanel(false);  // Open non-metal panel
      }

      delay(5000);  // Keep panel open for 5 seconds
      break;  // Close the panel and start over
    }

    delay(100);  // Delay between checks
  }
}

// Function to check if metal is detected by the sensor
bool isMetalDetected() {
  int sensorValue = digitalRead(metalSensorPin); // Read digital input
  Serial.print("Metal sensor value: ");
  Serial.println(sensorValue);

  // Return true if metal is detected (sensorValue == 0)
  return sensorValue == 0;
}

// Function to read distance from ultrasonic sensor
int readUltrasonicDistance() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  long duration = pulseIn(echoPin, HIGH);
  int distance = duration * 0.034 / 2;  // Convert duration to distance in cm
  return distance;
}

void movePlatePair(Servo &servoLeft, Servo &servoRight, 
                   int &leftPos, int &rightPos, 
                   int targetAngle, int speed,
                   float leftFactor, float rightFactor) {
  int leftTarget = closeAngleNoMetal - targetAngle;
  int rightTarget = targetAngle;
  
  int leftCurrent = closeAngleNoMetal - leftPos;
  int rightCurrent = rightPos;
  
  int leftSteps = abs(leftTarget - leftCurrent);
  int rightSteps = abs(rightTarget - rightCurrent);
  int maxSteps = max(leftSteps, rightSteps);
  
  float leftIncrement = leftSteps > 0 ? (leftTarget - leftCurrent) / (float)maxSteps : 0;
  float rightIncrement = rightSteps > 0 ? (rightTarget - rightCurrent) / (float)maxSteps : 0;
  
  float currentLeftPos = leftCurrent;
  float currentRightPos = rightCurrent;
  
  for (int step = 0; step < maxSteps; step++) {
    currentLeftPos += leftIncrement;
    currentRightPos += rightIncrement;
    
    servoLeft.write(round(currentLeftPos));
    servoRight.write(round(currentRightPos));
    
    int adjustedDelay = round(speed * max(leftFactor, rightFactor));
    delay(adjustedDelay);
  }
  
  servoLeft.write(leftTarget);
  servoRight.write(rightTarget); 
  
  leftPos = closeAngleNoMetal - leftTarget;
  rightPos = rightTarget;
}

void resetServos() {
  if (servo1LeftPos != closeAngleNoMetal || servo1RightPos != closeAngleNoMetal) {
    movePlatePair(servo1Left, servo1Right, servo1LeftPos, servo1RightPos, closeAngleNoMetal, baseSpeed,
                  noMetalPlateLeftFactor, noMetalPlateRightFactor);
  }
  if (servo2LeftPos != closeAngleMetal || servo2RightPos != closeAngleMetal) {
    movePlatePair(servo2Left, servo2Right, servo2LeftPos, servo2RightPos, closeAngleMetal, baseSpeed,
                  metalPlateLeftFactor, metalPlateRightFactor);
  }
}

void openPanel(bool isMetal) {
  if (isMetal) {
    Serial.println("Opening metal panel.");
    movePlatePair(servo1Left, servo1Right, servo1LeftPos, servo1RightPos, 90, baseSpeed,
                  noMetalPlateLeftFactor, noMetalPlateRightFactor);
  } else {
    Serial.println("Opening non-metal panel.");
    movePlatePair(servo2Left, servo2Right, servo2LeftPos, servo2RightPos, 90, baseSpeed,
                  metalPlateLeftFactor, metalPlateRightFactor);
  }
}

void returnToStart() {
  Serial.println("Returning servos to 90 degrees.");
  movePlatePair(servo1Left, servo1Right, servo1LeftPos, servo1RightPos, 90, baseSpeed,
                noMetalPlateLeftFactor, noMetalPlateRightFactor);
  movePlatePair(servo2Left, servo2Right, servo2LeftPos, servo2RightPos, 90, baseSpeed,
                metalPlateLeftFactor, metalPlateRightFactor);
}
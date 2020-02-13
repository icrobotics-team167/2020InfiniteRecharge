package frc.robot;

import frc.robot.controls.controllers.ControllerType;

public class Config {

    public static final class Settings {

        // Use Talon tank drive/test bot (false) or Spark tank drive/real bot (true)
        public static final boolean SPARK_TANK_ENABLED = false;

        // Controllers
        public static final ControllerType PRIMARY_CONTROLLER_TYPE = ControllerType.PS;
        public static final ControllerType SECONDARY_CONTROLLER_TYPE = ControllerType.NONE;

        // Dead zones
        public static final boolean TANK_DEAD_ZONE_ENABLED = false;
        public static final boolean TURRET_TURN_DEAD_ZONE_ENABLED = true;

        // CPU period (seconds)
        public static final double CPU_PERIOD = 0.02;

    }

    public static final class Tolerances {

        // Threshold for a controller trigger to count as pressed (on XB controllers only)
        public static final double TRIGGER_PRESSED_THRESHOLD = 0.2;

        // Tank drive dead zone size
        public static final double TANK_DEAD_ZONE_SIZE = 0.07;

        // Turret manual turn dead zone size
        public static final double TURRET_TURN_DEAD_ZONE_SIZE = 0.03;

    }

    public static final class Ports {

        // Controllers
        public static final int PRIMARY_CONTROLLER = 0;
        public static final int SECONDARY_CONTROLLER = 1; // if applicable

        public static final int PCM = 2;

        // Talon tank ports (motor controllers and encoders)
        public static final class TalonTank {
            public static final int LEFT_1 = 1;
            public static final int LEFT_2 = 2;
            public static final int LEFT_3 = 3;
            public static final int RIGHT_1 = 4;
            public static final int RIGHT_2 = 5;
            public static final int RIGHT_3 = 6;

            public static final int LEFT_ENCODER_A = 0;
            public static final int LEFT_ENCODER_B = 1;
            public static final boolean LEFT_ENCODER_REVERSED = true;
            public static final int RIGHT_ENCODER_A = 2;
            public static final int RIGHT_ENCODER_B = 3;
            public static final boolean RIGHT_ENCODER_REVERSED = true;
            public static final int SOLENOID = 2;
        }

        // Spark tank motor controller ports
        public static final class SparkTank {
            public static final int LEFT_1 = 3;
            public static final int LEFT_2 = 4;
            public static final int LEFT_3 = 5;
            public static final int RIGHT_1 = 6;
            public static final int RIGHT_2 = 7;
            public static final int RIGHT_3 = 8;
            public static final int SOLENOID_FORWARD = 1;
            public static final int SOLENOID_REVERSE = 2;
        }

        // Color Sensor
        public static final int COLOR_SENSOR = 5;

        // Intake ports
        public static final class Intake {
            public static final int MOTOR = 14;
            public static final int SOLENOID_FORWARD = 3;
            public static final int SOLENOID_REVERSE = 4;
        }

        // Turret motor controller port
        public static final int TURRET = 7;

        // Shooter motor controller ports
        public static final class Shooter {
            public static final int LEFT = 8;
            public static final int RIGHT = 9;
        }

        // LED PWM ports
        public static final int LED_1 = 0;

    }

}

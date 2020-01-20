package frc.robot;

import frc.robot.controls.controllers.ControllerType;

public class Config {

    public static final class Settings {

        // Use Talon tank drive/test bot (false) or Spark tank drive/real bot (true)
        public static final boolean SPARK_TANK_ENABLED = true;

        // Controllers
        public static final ControllerType PRIMARY_CONTROLLER_TYPE = ControllerType.XB;
        public static final ControllerType SECONDARY_CONTROLLER_TYPE = ControllerType.NONE;

        // Dead zones
        public static final boolean TANK_DEAD_ZONE_ENABLED = false;
//        public static final boolean SWERVE_MOVE_DEAD_ZONE_ENABLED = false;
//        public static final boolean SWERVE_SPIN_DEAD_ZONE_ENABLED = false;

    }

    public static final class Tolerances {

        // Threshold for a controller trigger to count as pressed
        public static final double TRIGGER_PRESSED_THRESHOLD = 0.2;

        // Tank drive dead zone size
        public static final double TANK_DEAD_ZONE_SIZE = 0.07;

//        // Swerve drive dead zones
//        public static final double SWERVE_MOVE_DEAD_ZONE_SIZE = 0.07;
//        public static final double SWERVE_SPIN_DEAD_ZONE_SIZE = 0.07;

    }

    public static final class Ports {

        // Controllers
        public static final int PRIMARY_CONTROLLER = 0;
        public static final int SECONDARY_CONTROLLER = 1; // if applicable

        // Talon tank ports (motor controllers and encoders)
        public static final class TalonTank {
            public static final int LEFT_1 = 3;
            public static final int LEFT_2 = 4;
            public static final int LEFT_3 = 9;
            public static final int RIGHT_1 = 6;
            public static final int RIGHT_2 = 7;
            public static final int RIGHT_3 = 8;

            public static final int LEFT_ENCODER_A = 0;
            public static final int LEFT_ENCODER_B = 1;
            public static final boolean LEFT_ENCODER_REVERSED = true;
            public static final int RIGHT_ENCODER_A = 2;
            public static final int RIGHT_ENCODER_B = 3;
            public static final boolean RIGHT_ENCODER_REVERSED = true;
        }

        // Spark tank motor controller ports
        public static final class SparkTank {
            public static final int LEFT_1 = 3;
            public static final int LEFT_2 = 4;
            public static final int LEFT_3 = 5;
            public static final int RIGHT_1 = 6;
            public static final int RIGHT_2 = 7;
            public static final int RIGHT_3 = 8;
        }

//        // Swerve module ports
//        public static final class Swerve {
//            public static final int FRONT_LEFT_SPIN = 7;
//            public static final int FRONT_LEFT_MOVE = 8;
//            public static final int FRONT_RIGHT_SPIN = 2;
//            public static final int FRONT_RIGHT_MOVE = 1;
//            public static final int BACK_LEFT_SPIN = 6;
//            public static final int BACK_LEFT_MOVE = 5;
//            public static final int BACK_RIGHT_SPIN = 4;
//            public static final int BACK_RIGHT_MOVE = 3;
//        }

        // Color Sensor
        public static final int COLOR_SENSOR = 5;

        // Intake motor controller pott
        public static final int INTAKE = 14;

        // Turret motor controller port
        public static final int TURRET = 5;

        // Shooter motor controller ports
        public static final class Shooter {
            public static final int LEFT = 6;
            public static final int RIGHT = 7;

            public static final int ENCODER_A = 6;
            public static final int ENCODER_B = 7;
            public static final int ENCODER_I = 8;
        }

        // LED PWM ports
        public static final int LED_1 = 0;

    }

    public static final class Measures {

//        // Swerve module locations (center on the origin, front facing the position x-axis)
//        public static final class Swerve {
//            public static final double FRONT_LEFT_X = 0.29845;
//            public static final double FRONT_LEFT_Y = 0.29845;
//            public static final double FRONT_RIGHT_X = 0.29845;
//            public static final double FRONT_RIGHT_Y = -0.29845;
//            public static final double BACK_LEFT_X = -0.29845;
//            public static final double BACK_LEFT_Y = 0.29845;
//            public static final double BACK_RIGHT_X = -0.29845;
//            public static final double BACK_RIGHT_Y = -0.29845;
//        }
//
//        // Swerve top speeds
//        public static final class SwerveSpeeds {
//            // TODO determine actual values
//            public static final double MOVE = 3.0; // meters per second
//            public static final double SPIN = 1.5; // radians per second
//        }

    }

}

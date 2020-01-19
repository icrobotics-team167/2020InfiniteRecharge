package frc.robot;

public class Config {

    // Use tank drive (false) or swerve drive (true)
    public static final boolean SWERVE_ENABLED = true;

    public static final class Tolerances {

        // Tank drive dead zone
        public static final boolean TANK_DEAD_ZONE_ENABLED = false;
        public static final double TANK_DEAD_ZONE_SIZE = 0.07;

        // Swerve drive dead zones
        public static final boolean SWERVE_MOVE_DEAD_ZONE_ENABLED = false;
        public static final double SWERVE_MOVE_DEAD_ZONE_SIZE = 0.07;
        public static final boolean SWERVE_SPIN_DEAD_ZONE_ENABLED = false;
        public static final double SWERVE_SPIN_DEAD_ZONE_SIZE = 0.07;

    }

    public static final class Ports {

        // Xbox controllers
        public static final int PRIMARY_CONTROLLER = 0;
        public static final int SECONDARY_CONTROLLER = 1; // if applicable

        // Tank motor controller ports
        public static final class Tank {
            public static final int LEFT_1 = 0;
            public static final int LEFT_2 = 1;
            public static final int LEFT_3 = 2;
            public static final int LEFT_4 = 3;
            public static final int RIGHT_1 = 4;
            public static final int RIGHT_2 = 5;
            public static final int RIGHT_3 = 6;
            public static final int RIGHT_4 = 7;
        }

        // Swerve module ports
        public static final class Swerve {
            public static final int FRONT_LEFT_SPIN = 7;
            public static final int FRONT_LEFT_MOVE = 8;
            public static final int FRONT_RIGHT_SPIN = 2;
            public static final int FRONT_RIGHT_MOVE = 1;
            public static final int BACK_LEFT_SPIN = 6;
            public static final int BACK_LEFT_MOVE = 5;
            public static final int BACK_RIGHT_SPIN = 4;
            public static final int BACK_RIGHT_MOVE = 3;
        }

        // Color Sensor
        public static final int COLOR_SENSOR = 5;

        // Turret motor controller port
        public static final int TURRET = 5;

    }

    public static final class Measures {

        // Swerve module locations (center on the origin, front facing the position x-axis)
        public static final class Swerve {
            public static final double FRONT_LEFT_X = 0.29845;
            public static final double FRONT_LEFT_Y = 0.29845;
            public static final double FRONT_RIGHT_X = 0.29845;
            public static final double FRONT_RIGHT_Y = -0.29845;
            public static final double BACK_LEFT_X = -0.29845;
            public static final double BACK_LEFT_Y = 0.29845;
            public static final double BACK_RIGHT_X = -0.29845;
            public static final double BACK_RIGHT_Y = -0.29845;
        }

        // Swerve top speeds
        public static final class SwerveSpeeds {
            // TODO determine actual values
            public static final double MOVE = 3.0; // meters per second
            public static final double SPIN = 1.5; // radians per second
        }

    }

}

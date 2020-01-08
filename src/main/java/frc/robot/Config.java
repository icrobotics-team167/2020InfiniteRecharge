package frc.robot;

public class Config {

    public static final class Tolerances {

        // Maximum difference in each RGB value between two colors (0-255)
        public static final int COLOR_EQUIVALENCE = 25;

    }

    public static final class Ports {

        // Xbox controllers
        public static final int PRIMARY_CONTROLLER = 0;
        
        // Swerve module ports
        public static final class Swerve {
            public static final int FRONT_LEFT_SPIN = 0;
            public static final int FRONT_LEFT_MOVE = 1;
            public static final int FRONT_RIGHT_SPIN = 2;
            public static final int FRONT_RIGHT_MOVE = 3;
            public static final int BACK_LEFT_SPIN = 4;
            public static final int BACK_LEFT_MOVE = 5;
            public static final int BACK_RIGHT_SPIN = 6;
            public static final int BACK_RIGHT_MOVE = 7;
        }

    }

    public static final class Measures {

        // Swerve module locations (center on the origin, front facing the position x-axis)
        public static final class Swerve {
            // TODO determine actual values
            public static final double FRONT_LEFT_X = 0.381;
            public static final double FRONT_LEFT_Y = 0.381;
            public static final double FRONT_RIGHT_X = 0.381;
            public static final double FRONT_RIGHT_Y = -0.381;
            public static final double BACK_LEFT_X = -0.381;
            public static final double BACK_LEFT_Y = 0.381;
            public static final double BACK_RIGHT_X = -0.381;
            public static final double BACK_RIGHT_Y = -0.381;
        }

        // Swerve top speeds
        public static final class SwerveSpeeds {
            // TODO determine actual values
            public static final double MOVE = 3.0; // meters per second
            public static final double SPIN = 1.5; // radians per second
        }

    }

}

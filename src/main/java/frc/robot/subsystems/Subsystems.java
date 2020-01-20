package frc.robot.subsystems;

import frc.robot.Config;
import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TalonTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Turret turret;

    static {
        if (Config.Settings.SPARK_TANK_ENABLED) {
            driveBase = SparkTankDriveBase.getInstance();
        } else {
            driveBase = TalonTankDriveBase.getInstance();
        }

        turret = Turret.getInstance();
    }

}

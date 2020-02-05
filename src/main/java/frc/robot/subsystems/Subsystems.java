package frc.robot.subsystems;

import frc.robot.Config;
import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TalonTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Intake intake;
    public static final Turret turret;
    public static final Shooter shooter;

    static {
        if (Config.Settings.SPARK_TANK_ENABLED) {
            driveBase = SparkTankDriveBase.getInstance();
        } else {
            driveBase = TalonTankDriveBase.getInstance();
        }

        intake = Intake.getInstance();

        turret = Turret.getInstance();

        shooter = Shooter.getInstance();
    }

}

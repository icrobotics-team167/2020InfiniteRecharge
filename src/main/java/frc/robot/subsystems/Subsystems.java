package frc.robot.subsystems;

import frc.robot.Config;
import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TalonTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Intake intake;
    public static final Indexer indexer;
    public static final Limelight limelight;
    public static final Turret turret;
    public static final Shooter shooter;
    public static final Climber climber;

    static {
        if (Config.Settings.SPARK_TANK_ENABLED) {
            driveBase = SparkTankDriveBase.getInstance();
        } else {
            driveBase = TalonTankDriveBase.getInstance();
        }

        intake = Intake.getInstance();

        indexer = Indexer.getInstance();

        limelight = Limelight.getInstance();

        turret = Turret.getInstance();

        shooter = Shooter.getInstance();

        climber = Climber.getInstance();
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        turret.setMode(Turret.Mode.OFF);
        shooter.stop();
    }

    public static void setClimberStates() {
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        turret.setMode(Turret.Mode.OFF);
        shooter.stop();
    }

}

package frc.robot.routines.auto;

import frc.robot.Config;
import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.Turret;

public class StartShooter extends Action {

    public StartShooter() {
        super();
    }

    @Override
    public void init() {
        Subsystems.driveBase.straightDrive(0);
        Subsystems.intake.extend();
        Subsystems.intake.setMode(Intake.Mode.FORWARD);
        Subsystems.indexer.setMode(Indexer.Mode.OFF);
        Subsystems.shooter.setTargetRPM(Config.Settings.SICKO_SHOOTING_RPM);
        Subsystems.shooter.start();
        Subsystems.limelight.setVisionMode();
        Subsystems.turret.setMode(Turret.Mode.AUTO_ALIGN);
    }

    @Override
    public void periodic() {
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void done() {}

}

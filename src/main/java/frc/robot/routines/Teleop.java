package frc.robot.routines;

import frc.robot.controls.inputs.ControlScheme;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.drive.TankDriveBase;

public class Teleop {

    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Intake intake;
    private Turret turret;
    private Shooter shooter;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        intake = Subsystems.intake;
        turret = Subsystems.turret;
        shooter = Subsystems.shooter;
    }



    private boolean turretAutoAlignEnabled = false;

    public void init() {
        turretAutoAlignEnabled = false;
    }

    public void periodic() {
        driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());

        if (controls.doRunIntakeForward()) {
            intake.runForward();
        } else if (controls.doRunIntakeReverse()) {
            intake.runReverse();
        } else {
            intake.stop();
        }

        if (controls.doRunShooter()) {
            shooter.drive(3700);
        } else {
            shooter.stop();
        }
        shooter.printEncoderValues();

        if (controls.doToggleTurretAutoAlign()) {
            turretAutoAlignEnabled = !turretAutoAlignEnabled;
        }
        if (controls.doTurnTurretClockwise()) {
            turret.turnClockwise(0.3);
            turretAutoAlignEnabled = false;
        } else if (controls.doTurnTurretCounterclockwise()) {
            turret.turnCounterclockwise(0.3);
            turretAutoAlignEnabled = false;
        } else if (turretAutoAlignEnabled) {
            turret.autoAlign();
        } else {
            turret.stop();
        }
    }

    public void done() {

    }

    public void disabledPeriodic() {

    }

}

package frc.robot.routines;

import frc.robot.controls.controlschemes.ControlScheme;
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

    private boolean shooterEnabled = false;
    private boolean turretAutoAlignEnabled = false;

    public void init() {
        turretAutoAlignEnabled = false;
    }

    public void periodic() {
        driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());

        if (controls.doToggleIntakeExtended()) {
            intake.toggleExtension();
        }
        if (controls.doToggleIntakeForward()) {
            if (intake.isRunningForward()) {
                intake.stop();
            } else {
                intake.runForward();
            }
        } else if (controls.doToggleIntakeReverse()) {
            if (intake.isRunningReverse()) {
                intake.stop();
            } else {
                intake.runReverse();
            }
        } else {
            if (intake.isRunningForward()) {
                intake.runForward();
            } else if (intake.isRunningReverse()) {
                intake.runReverse();
            } else {
                intake.stop();
            }
        }

        if (shooterEnabled && controls.doToggleShooter()) {
            shooter.stop();
            shooterEnabled = false;
        } else if (!shooterEnabled && controls.doToggleShooter()) {
            shooter.drive(3700);
            shooterEnabled = true;
        } else if (shooterEnabled) {
            shooter.drive(3700);
            shooterEnabled = true;
        } else {
            shooter.stop();
            shooterEnabled = false;
        }

        if (controls.doToggleTurretAutoAlign()) {
            turretAutoAlignEnabled = !turretAutoAlignEnabled;
        }
        if (controls.doTurnTurretClockwise()) {
            turret.turnClockwise(controls.getTurretClockwiseSpeed());
            turretAutoAlignEnabled = false;
        } else if (controls.doTurnTurretCounterclockwise()) {
            turret.turnCounterclockwise(controls.getTurretCounterclockwiseSpeed());
            turretAutoAlignEnabled = false;
        } else if (turretAutoAlignEnabled) {
            turret.autoAlign();
        } else {
            turret.stop();
        }
    }

}

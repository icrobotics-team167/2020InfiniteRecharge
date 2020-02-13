package frc.robot.routines;

import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;

public class Teleop {

    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Intake intake;
    private Indexer indexer;
    private Turret turret;
    private Shooter shooter;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        intake = Subsystems.intake;
        indexer = Subsystems.indexer;
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
        if (controls.doToggleGearing()) {
            driveBase.toggleGearing();
        }

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

        if (intake.isRunningForward()) {
            indexer.turnIntakeSpeed();
            indexer.stopShooting();
        } else if (shooterEnabled) {
            indexer.turnShootingSpeed();
            indexer.shoot();
        } else if (intake.isRunningReverse()) {
            indexer.turnIntakeSpeed();
            indexer.stopShooting();
        } else {
            indexer.stopTurning();
            indexer.stopShooting();
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

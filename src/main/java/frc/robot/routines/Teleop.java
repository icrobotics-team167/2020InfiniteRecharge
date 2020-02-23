package frc.robot.routines;

import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.SparkTankDriveBase;
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

    public void init() {
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        turret.setMode(Turret.Mode.OFF);
        shooter.stop();
    }

    public void periodic() {
        driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        // if (driveBase instanceof SparkTankDriveBase) {
        //     ((SparkTankDriveBase) driveBase).testMotor();
        // }

        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        }

        if (controls.doToggleIntakeExtension()) {
            intake.toggleExtension();
        }

        if (controls.doGroundIntake()) {
            intake.extend();
            intake.setMode(Intake.Mode.FORWARD);
            indexer.setMode(Indexer.Mode.SMART_INTAKE);
        } else if (controls.doHumanPlayerIntake()) {
            intake.retract();
            intake.setMode(Intake.Mode.FORWARD);
            indexer.setMode(Indexer.Mode.SMART_INTAKE);
        } else if (controls.doToggleIndexerAlignMode()) {
            if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT) {
                indexer.setMode(Indexer.Mode.OFF);
            } else if (indexer.getMode() == Indexer.Mode.OFF) {
                indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
            }
        } else if (controls.doIndexerShooterMode() && indexer.isGapAligned() && shooter.isUpToSpeed()) {
            indexer.setMode(Indexer.Mode.SMART_SHOOT);
        } else {
            indexer.setMode(Indexer.Mode.MANUAL);
            indexer.setIndexerSpeed(controls.getIndexerSpeed());
            indexer.setLiftSpeed(controls.doLiftMotorForward() ? 0.35 : (controls.doLiftMotorReverse() ? -0.35 : 0));

            intake.setMode(Intake.Mode.MANUAL);
            intake.setSpeed(controls.getIntakeSpeed());
        }
        intake.run();
        indexer.run();

        if (controls.doToggleShooter()) {
            shooter.toggle();
        }
        shooter.run();

        if (controls.doAutoAlignTurret()) {
            turret.setMode(Turret.Mode.AUTO_ALIGN);
        } else if (controls.doTurnTurretClockwise()) {
            turret.setMode(Turret.Mode.TURN_CLOCKWISE);
        } else if (controls.doTurnTurretCounterclockwise()) {
            turret.setMode(Turret.Mode.TURN_COUNTERCLOCKWISE);
        } else {
            turret.setMode(Turret.Mode.OFF);
        }
        turret.run();
    }

}

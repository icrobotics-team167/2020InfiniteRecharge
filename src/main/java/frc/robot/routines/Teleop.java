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

    public void init() {
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        turret.setMode(Turret.Mode.OFF);
        shooter.stop();
    }

    public void periodic() {
        // Drive base
        driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        } else if (controls.doToggleGearing()) {
            driveBase.toggleGearing();
        }

        // Intake and indexer
        if (controls.doToggleIntakeExtension()) {
            intake.toggleExtension();
        } else if (controls.doToggleGroundIntakeExtension()) {
            intake.extend();
        } else if (controls.doToggleHumanPlayerIntakeRetraction()) {
            intake.retract();
        }
        if (controls.doGroundIntake() || controls.doHumanPlayerIntake()) {
            intake.setMode(Intake.Mode.FORWARD);
            if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT && !indexer.isGapAligned()) {
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            }
        } else if (controls.doRunIntakeManually()) {
            intake.setManualSpeed(controls.getIntakeManualSpeed());
            intake.setMode(Intake.Mode.MANUAL);
        } else {
            intake.setMode(Intake.Mode.OFF);
        }
        intake.run();
        if (controls.doRunIndexerManually() || controls.doLiftMotorForwardManually() || controls.doLiftMotorReverseManually()) {
            indexer.setManualTurnSpeed(controls.getIndexerManualSpeed());
            if (controls.doLiftMotorForwardManually()) {
                indexer.setManualLiftSpeed(0.35);
            } else if (controls.doLiftMotorReverseManually()) {
                indexer.setManualLiftSpeed(-0.35);
            } else {
                indexer.setManualLiftSpeed(0);
            }
            indexer.setMode(Indexer.Mode.MANUAL);
        } else if (controls.doIndexerShooterMode()) {
            if ((indexer.isGapAligned() && shooter.isUpToSpeed()) || indexer.getMode() == Indexer.Mode.SMART_SHOOT) {
                indexer.setMode(Indexer.Mode.SMART_SHOOT);
            }
        } else if (controls.doToggleIndexerAlignMode()) {
            if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT) {
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
            }
        } else if (indexer.getMode() != Indexer.Mode.GAP_ALIGNMENT && !controls.doGroundIntake() && !controls.doHumanPlayerIntake()) {
            indexer.setMode(Indexer.Mode.OFF);
        }
        indexer.run();

        // Shooter
        if (controls.doToggleShooter()) {
            shooter.toggle();
        }
        shooter.run();

        // Turret
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

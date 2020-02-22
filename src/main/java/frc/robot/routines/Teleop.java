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
        if (controls.doToggleIntakeForward()) {
            if (intake.getMode() == Intake.Mode.FORWARD) {
                intake.setMode(Intake.Mode.OFF);
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                intake.setMode(Intake.Mode.FORWARD);
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            }
        } else if (controls.doToggleIntakeReverse()) {
            if (intake.getMode() == Intake.Mode.REVERSE) {
                intake.setMode(Intake.Mode.OFF);
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                intake.setMode(Intake.Mode.REVERSE);
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            }
        }
        intake.run();

        if (controls.doIndexerForward() && controls.doLiftMotorForward()) {
            indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_FORWARD);
        } else if (controls.doIndexerForward() && controls.doLiftMotorReverse()) {
            indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_REVERSE);
        } else if (controls.doIndexerForward()) {
            indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_OFF);
        } else if (controls.doIndexerReverse() && controls.doLiftMotorForward()) {
            indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_FORWARD);
        } else if (controls.doIndexerReverse() && controls.doLiftMotorReverse()) {
            indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_REVERSE);
        } else if (controls.doIndexerReverse()) {
            indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_OFF);
        } else if (controls.doLiftMotorForward()) {
            indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_FORWARD);
        } else if (controls.doLiftMotorReverse()) {
            indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_REVERSE);
        } else if (controls.doToggleIndexerAlignMode()) {
            if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT) {
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
            }
        } else if (controls.doToggleIndexerShooterMode()) {
            if (indexer.getMode() == Indexer.Mode.SMART_SHOOT) {
                indexer.setMode(Indexer.Mode.OFF);
            } else if (indexer.isGapAligned() && shooter.isUpToSpeed()) {
                indexer.setMode(Indexer.Mode.SMART_SHOOT);
            }
        } else if (indexer.getMode() != Indexer.Mode.GAP_ALIGNMENT && indexer.getMode() != Indexer.Mode.SMART_SHOOT && indexer.getMode() != Indexer.Mode.SMART_INTAKE) {
            indexer.setMode(Indexer.Mode.OFF);
        }
        // if (controls.doToggleIndexerAlignMode()) {
        //     if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else {
        //         indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
        //     }
        // } else if (controls.doToggleIndexerShooterMode()) {
        //     if (indexer.getMode() == Indexer.Mode.SMART_SHOOT) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else if (shooter.isUpToSpeed() && indexer.isGapAligned()) {
        //         indexer.setMode(Indexer.Mode.SMART_SHOOT);
        //     }
        // } else if (controls.doIndexerForward()) {
        //     Indexer.Mode currentMode = indexer.getMode();
        //     if (currentMode == Indexer.Mode.TURN_OFF_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_OFF_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_REVERSE);
        //     } else {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_OFF);
        //     }
        // } else if (controls.doIndexerReverse()) {
        //     Indexer.Mode currentMode = indexer.getMode();
        //     if (currentMode == Indexer.Mode.TURN_OFF_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_OFF_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_REVERSE);
        //     } else {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_OFF);
        //     }
        // } else if (controls.doLiftMotorForward()) {
        //     Indexer.Mode currentMode = indexer.getMode();
        //     if (currentMode == Indexer.Mode.TURN_OFF_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_OFF_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_FORWARD);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_FORWARD);
        //     } else {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_FORWARD);
        //     }
        // } else if (controls.doLiftMotorReverse()) {
        //     Indexer.Mode currentMode = indexer.getMode();
        //     if (currentMode == Indexer.Mode.TURN_OFF_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_OFF_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_FORWARD_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_FORWARD_LIFT_OFF);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_OFF) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_FORWARD) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_REVERSE);
        //     } else if (currentMode == Indexer.Mode.TURN_REVERSE_LIFT_REVERSE) {
        //         indexer.setMode(Indexer.Mode.TURN_REVERSE_LIFT_OFF);
        //     } else {
        //         indexer.setMode(Indexer.Mode.TURN_OFF_LIFT_REVERSE);
        //     }
        // }
        indexer.run();

        if (controls.doToggleShooter()) {
            shooter.toggle();
        }
        shooter.run();

        if (controls.doAutoAlignTurret()) {
            turret.setMode(Turret.Mode.AUTO_ALIGN);
            // if (turret.getMode() == Turret.Mode.AUTO_ALIGN) {
            //     turret.setMode(Turret.Mode.OFF);
            // } else {
            //     turret.setMode(Turret.Mode.AUTO_ALIGN);
            // }
        } else if (controls.doTurnTurretClockwise()) {
            turret.setMode(Turret.Mode.TURN_CLOCKWISE);
        } else if (controls.doTurnTurretCounterclockwise()) {
            turret.setMode(Turret.Mode.TURN_COUNTERCLOCKWISE);
        // } else if (turret.getMode() == Turret.Mode.AUTO_ALIGN) {
        //     turret.setMode(Turret.Mode.AUTO_ALIGN);
        } else {
            turret.setMode(Turret.Mode.OFF);
        }
        turret.run();
    }

}

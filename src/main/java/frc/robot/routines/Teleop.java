package frc.robot.routines;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
            // if (indexer.getMode() == Indexer.Mode.SMART_INTAKE) {
            //     indexer.setMode(Indexer.Mode.OFF);
            // } else {
            //     indexer.setMode(Indexer.Mode.SMART_INTAKE);
            // }
// remove maybe

            if (intake.getMode() == Intake.Mode.FORWARD) {
                intake.setMode(Intake.Mode.OFF);
            } else {
                intake.setMode(Intake.Mode.FORWARD);
            }
        } else if (controls.doToggleIntakeReverse()) {
            if (intake.getMode() == Intake.Mode.REVERSE) {
                intake.setMode(Intake.Mode.OFF);
            } else {
                intake.setMode(Intake.Mode.REVERSE);
            }
        }
        intake.run();

        if (controls.doToggleIndexerIntakeMode()) {
            if (indexer.getMode() == Indexer.Mode.SMART_INTAKE) {
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            }
        } else if (controls.doToggleIndexerShooterMode()) {
            if (indexer.getMode() == Indexer.Mode.SMART_SHOOT) {
                indexer.setMode(Indexer.Mode.OFF);
            } else {
                indexer.setMode(Indexer.Mode.SMART_SHOOT);
            }
        }
        

        if (controls.doToggleShooter()) {
            shooter.toggle();
        }
        // if (controls.doToggleShooter()) {
        //     if (indexer.getMode() == Indexer.Mode.SMART_INTAKE) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //     } else {
        //         indexer.setMode(Indexer.Mode.SMART_INTAKE);
        //     }

        //     if (indexer.getMode() == Indexer.Mode.OFF) {
        //         indexer.setMode(Indexer.Mode.SMART_INTAKE);
        //     } else if (indexer.getMode() == Indexer.Mode.SMART_INTAKE) {
        //         // indexer.setMode(Indexer.Mode.SHOOTER_STARTUP);
        //         indexer.setMode(Indexer.Mode.SMART_SHOOT);
        //         shooter.start();
        //     } else if (indexer.getMode() == Indexer.Mode.SHOOTER_STARTUP) {
        //         if (indexer.isReadyToShoot() && shooter.isUpToSpeed()) {
        //             indexer.setMode(Indexer.Mode.SMART_SHOOT);
        //         }
        //         // Make sure the shooter continues running (although it should already be started)
        //         // shooter.start();
        //     } else {
        //         indexer.setMode(Indexer.Mode.OFF);
        //         shooter.stop();
        //     }
        // }
        indexer.run();
        shooter.run();

        if (controls.doToggleTurretAutoAlign()) {
            if (turret.getMode() == Turret.Mode.AUTO_ALIGN) {
                turret.setMode(Turret.Mode.OFF);
            } else {
                turret.setMode(Turret.Mode.AUTO_ALIGN);
            }
        } else if (controls.doTurnTurretClockwise()) {
            turret.setMode(Turret.Mode.TURN_CLOCKWISE);
        } else if (controls.doTurnTurretCounterclockwise()) {
            turret.setMode(Turret.Mode.TURN_COUNTERCLOCKWISE);
        } else if (turret.getMode() == Turret.Mode.AUTO_ALIGN) {
            turret.setMode(Turret.Mode.AUTO_ALIGN);
        } else {
            turret.setMode(Turret.Mode.OFF);
        }
        turret.run();


        // new SpeedControllerGroup(speedController, speedControllers)
    }

}

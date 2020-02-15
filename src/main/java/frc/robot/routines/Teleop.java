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

        if (controls.doToggleIntakeForward()) {
            switch (intake.getMode()) {
                case OFF_UP:
                    intake.setMode(Intake.Mode.INTAKE_DOWN);
                    break;
                case OFF_DOWN:
                    intake.setMode(Intake.Mode.INTAKE_DOWN);
                    break;
                case INTAKE_DOWN:
                    intake.setMode(Intake.Mode.OFF_DOWN);
                    break;
                case REVERSE_UP:
                    intake.setMode(Intake.Mode.INTAKE_DOWN);
                    break;
                case REVERSE_DOWN:
                    intake.setMode(Intake.Mode.INTAKE_DOWN);
                    break;
                default:
                    break;
            }
        } else if (controls.doToggleIntakeReverse()) {
            switch (intake.getMode()) {
                case OFF_UP:
                    intake.setMode(Intake.Mode.REVERSE_UP);
                    break;
                case OFF_DOWN:
                    intake.setMode(Intake.Mode.REVERSE_UP);
                    break;
                case INTAKE_DOWN:
                    intake.setMode(Intake.Mode.REVERSE_DOWN);
                    break;
                case REVERSE_UP:
                    intake.setMode(Intake.Mode.OFF_UP);
                    break;
                case REVERSE_DOWN:
                    intake.setMode(Intake.Mode.OFF_DOWN);
                    break;
                default:
                    break;
            }
        } else if (controls.doToggleIntakeDown()) {
            switch (intake.getMode()) {
                case OFF_UP:
                    intake.setMode(Intake.Mode.OFF_DOWN);
                    break;
                case OFF_DOWN:
                    intake.setMode(Intake.Mode.OFF_UP);
                    break;
                case INTAKE_DOWN:
                    intake.setMode(Intake.Mode.OFF_UP);
                    break;
                case REVERSE_UP:
                    intake.setMode(Intake.Mode.REVERSE_DOWN);
                    break;
                case REVERSE_DOWN:
                    intake.setMode(Intake.Mode.REVERSE_UP);
                    break;
                default:
                    break;
            }
        }

        if (controls.doToggleShooter()) {
            shooter.toggle();
            if (shooter.isInShootingMode()) {
                indexer.setMode(Indexer.Mode.SMART_SHOOT);
            } else {
                indexer.setMode(Indexer.Mode.OFF);
            }
        }
        indexer.run();
        shooter.run();

        if (controls.doToggleTurretAutoAlign() && turret.getMode() != Turret.Mode.AUTO_ALIGN) {
            turret.setMode(Turret.Mode.AUTO_ALIGN);
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
    }

}

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

    private boolean shooterEnabled = false;
    private boolean indexerTestEnabled = false;
    private boolean turretAutoAlignEnabled = false;
    private boolean intakeDown = false;
    private boolean smartShoot = false;

    public void init() {
        turretAutoAlignEnabled = false;
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
        // intake.run();

        // switch (intake.getMode()) {
        //     case OFF_UP:
        //         indexer.setMode(Indexer.Mode.OFF);
        //         break;
        //     case OFF_DOWN:
        //         indexer.setMode(Indexer.Mode.OFF);
        //         break;
        //     case INTAKE_DOWN:
        //         indexer.setMode(Indexer.Mode.SMART_INTAKE);
        //         break;
        //     case REVERSE_UP:
        //         indexer.setMode(Indexer.Mode.SMART_INTAKE);
        //         break;
        //     case REVERSE_DOWN:
        //         indexer.setMode(Indexer.Mode.SMART_SHOOT);
        //         break;
        //     default:
        //         break;
        // }
        // if (controls.doToggleIntakeForward()) {
        //     if (intakeDown) {
        //         intake.setMode(Intake.Mode.OFF_UP);
        //         intakeDown = false;
        //     } else {
        //         intake.setMode(Intake.Mode.INTAKE_DOWN);
        //         intakeDown = true;
        //     }
        // }
        // intake.run();
        // if (controls.doToggleIntakeReverse()) {
        //     if (smartShoot) {
        //         indexer.setMode(Indexer.Mode.OFF);
        //         smartShoot = false;
        //     } else {
        //         indexer.setMode(Indexer.Mode.SHOOT_FORWARD);
        //         smartShoot = true;
        //     }
        // }
        // indexer.run();

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

        // if (controls.doToggleTurretAutoAlign()) {
        //     turretAutoAlignEnabled = !turretAutoAlignEnabled;
        // }
        // if (controls.doTurnTurretClockwise()) {
        //     turret.turnClockwise(0.3);
        //     turretAutoAlignEnabled = false;
        // } else if (controls.doTurnTurretCounterclockwise()) {
        //     turret.turnCounterclockwise(0.3);
        //     turretAutoAlignEnabled = false;
        // } else if (turretAutoAlignEnabled) {
        //     turret.autoAlign();
        // } else {
        //     turret.stop();
        // }

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

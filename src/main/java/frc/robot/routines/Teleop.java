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
    private boolean indexerTestEnabled = false;
    private boolean turretAutoAlignEnabled = false;

    public void init() {
        turretAutoAlignEnabled = false;
    }

    public void periodic() {
        driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        if (controls.doToggleGearing()) {
            driveBase.toggleGearing();
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
        intake.run();

        switch (intake.getMode()) {
            case OFF_UP:
                indexer.setMode(Indexer.Mode.OFF);
                break;
            case OFF_DOWN:
                indexer.setMode(Indexer.Mode.OFF);
                break;
            case INTAKE_DOWN:
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
                break;
            case REVERSE_UP:
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
                break;
            case REVERSE_DOWN:
                indexer.setMode(Indexer.Mode.SMART_SHOOT);
                break;
            default:
                break;
        }
        indexer.run();

        if (controls.doToggleShooter()) {
            shooterEnabled = !shooterEnabled;
        }
        if (shooterEnabled) {
            shooter.drive(3700);
        } else {
            shooter.stop();
        }

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

}

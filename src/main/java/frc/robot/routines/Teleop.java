package frc.robot.routines;

import frc.robot.Config;
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
    private Climber climber;
    private boolean climbMode;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        intake = Subsystems.intake;
        indexer = Subsystems.indexer;
        turret = Subsystems.turret;
        shooter = Subsystems.shooter;
        climber = Subsystems.climber;
    }

    public void init() {
        driveBase.setHighGear();
        driveBase.resetEncoders();
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        turret.setMode(Turret.Mode.OFF);
        shooter.stop();
        climbMode = false;
    }

    public void periodic() {
        // Drive base
        if (controls.doStraightDrive()) {
            // Straight drive is inverted because it is designed for autonomous where
            // the intake is the front instead of the shooter
            driveBase.straightDrive(-controls.getTankLeftSpeed());
        } else {
            driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        }
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        } else if (controls.doToggleGearing()) {
            driveBase.toggleGearing();
        }

        if (!climbMode) {
            // Intake and indexer
            if (controls.doToggleIntakeExtension()) {
                intake.toggleExtension();
            } else if (controls.doToggleGroundIntakeExtension()) {
                intake.extend();
                intake.setMode(Intake.Mode.FORWARD);
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            } else if (controls.doToggleHumanPlayerIntakeRetraction()) {
                intake.retract();
                intake.setMode(Intake.Mode.FORWARD);
                indexer.setMode(Indexer.Mode.SMART_INTAKE);
            }
            if (controls.doGroundIntake() || controls.doHumanPlayerIntake()) {
                intake.setMode(Intake.Mode.FORWARD);
                if (indexer.getMode() != Indexer.Mode.GAP_ALIGNMENT && indexer.getMode() != Indexer.Mode.SICKO_SHOOT) {
                    indexer.setMode(Indexer.Mode.SMART_INTAKE);
                }
                // if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT && !indexer.isGapAligned()) {
                //     indexer.setMode(Indexer.Mode.SMART_INTAKE);
                // }
            } else if (controls.doRunIntakeManually()) {
                intake.setManualSpeed(controls.getIntakeManualSpeed());
                intake.setMode(Intake.Mode.MANUAL);
            } else {
                intake.setMode(Intake.Mode.OFF);
            }

            if (controls.doAntiJamServoReverseManually()) {
                indexer.reverseAntiJamServo();
            } else {
                indexer.forwardAntiJamServo();
            }
            if (controls.doRunIndexerManually() || controls.doLiftMotorForwardManually() || controls.doLiftMotorReverseManually()) {
                indexer.setManualTurnSpeed(controls.getIndexerManualSpeed());
                if (controls.doLiftMotorForwardManually()) {
                    indexer.setManualLiftSpeed(1);
                } else if (controls.doLiftMotorReverseManually()) {
                    indexer.setManualLiftSpeed(-0.595);
                } else {
                    indexer.setManualLiftSpeed(0);
                }
                indexer.setMode(Indexer.Mode.MANUAL);
            } else if (controls.doIndexerSickoShootMode()) {
                indexer.setMode(Indexer.Mode.SICKO_SHOOT);
                shooter.setTargetRPM(Config.Settings.SICKO_SHOOTING_RPM);
//                if ((indexer.isGapAligned() && shooter.isUpToSpeed()) || indexer.getMode() == Indexer.Mode.SICKO_SHOOT) {
//                    indexer.setMode(Indexer.Mode.SICKO_SHOOT);
//                }
            } else if (controls.doIndexerSingleTurn()) {
                indexer.setMode(Indexer.Mode.SINGLE_TURN);
            } else if (controls.doToggleIndexerAlignMode()) {
                if (indexer.getMode() == Indexer.Mode.GAP_ALIGNMENT) {
                    indexer.setMode(Indexer.Mode.OFF);
                } else {
                    indexer.setMode(Indexer.Mode.GAP_ALIGNMENT);
                }
            } else if (indexer.getMode() != Indexer.Mode.GAP_ALIGNMENT && !controls.doGroundIntake() && !controls.doHumanPlayerIntake()) {
                indexer.setMode(Indexer.Mode.OFF);
            }

            // Shooter
            if (controls.doToggleShooter()) {
                shooter.toggle();
            }

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
        } else /* if (climbMode) */ {
            if (controls.doRaiseClimber()) {
                climber.raiseExtension();
            } else if (controls.doLowerClimber()) {
                climber.lowerExtension();
            } else {
                climber.stopExtension();
            }
            if (controls.doClimb()) {
                climber.climbUp();
            } else {
                climber.stopClimb();
            }
        }

        // Climb mode toggle
        if (controls.doToggleClimbMode()) {
            climbMode = !climbMode;
        }

        // Run
        intake.run();
        indexer.run();
        shooter.run();
        turret.run();
    }

}

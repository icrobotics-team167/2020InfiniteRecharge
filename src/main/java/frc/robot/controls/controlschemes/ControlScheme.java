package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    // Drive base
    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();
    public abstract boolean doSwitchHighGear();
    public abstract boolean doSwitchLowGear();
    public abstract boolean doToggleGearing();

    // Intake
    public abstract boolean doToggleIntakeExtension();
    public abstract boolean doGroundIntake();
    public abstract boolean doToggleGroundIntakeExtension();
    public abstract boolean doHumanPlayerIntake();
    public abstract boolean doToggleHumanPlayerIntakeRetraction();
    public abstract boolean doRunIntakeManually();
    public abstract double getIntakeManualSpeed();

    // Indexer
    public abstract boolean doToggleIndexerAlignMode();
    public abstract boolean doIndexerShooterMode();
    public abstract boolean doIndexerSickoShootMode();
    public abstract boolean doRunIndexerManually();
    public abstract double getIndexerManualSpeed();
    public abstract boolean doLiftMotorForwardManually();
    public abstract boolean doLiftMotorReverseManually();
    public abstract boolean doAntiJamReverse();

    // Shooter
    public abstract boolean doToggleShooter();

    // Turret
    public abstract boolean doAutoAlignTurret();
    public abstract boolean doTurnTurretClockwise();
    public abstract boolean doTurnTurretCounterclockwise();

    // Climb
    public abstract boolean doToggleClimbExtension();
    public abstract boolean doClimbUp();
    public abstract boolean doClimbDown();
    public abstract boolean doClimbReset();

}

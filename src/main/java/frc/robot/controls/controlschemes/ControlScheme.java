package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();

    public abstract boolean doSwitchHighGear();
    public abstract boolean doSwitchLowGear();

    public abstract boolean doToggleIntakeExtension();

    public abstract boolean doGroundIntake();
    public abstract boolean doHumanPlayerIntake();

    public abstract boolean doToggleIndexerAlignMode();
    public abstract boolean doIndexerShooterMode();

    public abstract double getIndexerSpeed();
    public abstract double getIntakeSpeed();
    public abstract boolean doLiftMotorForward();
    public abstract boolean doLiftMotorReverse();

    public abstract boolean doToggleShooter();

    public abstract boolean doAutoAlignTurret();
    public abstract boolean doTurnTurretClockwise();
    public abstract boolean doTurnTurretCounterclockwise();



}

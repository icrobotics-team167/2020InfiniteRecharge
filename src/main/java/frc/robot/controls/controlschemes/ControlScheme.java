package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();
    public abstract boolean doSwitchHighGear();
    public abstract boolean doSwitchLowGear();

    public abstract boolean doToggleIntakeExtension();
    public abstract boolean doToggleIntakeForward();
    public abstract boolean doToggleIntakeReverse();

    public abstract boolean doToggleIndexerAlignMode();
    public abstract boolean doToggleIndexerShooterMode();
    public abstract boolean doToggleIndexerForward();
    public abstract boolean doToggleIndexerReverse();
    public abstract boolean doToggleLiftMotorForward();
    public abstract boolean doToggleLiftMotorReverse();

    public abstract boolean doToggleShooter();

    public abstract boolean doToggleTurretAutoAlign();
    public abstract boolean doTurnTurretClockwise();
    public abstract boolean doTurnTurretCounterclockwise();

}

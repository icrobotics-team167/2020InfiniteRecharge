package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();
    public abstract boolean doSwitchHighGear();
    public abstract boolean doSwitchLowGear();

    public abstract boolean doToggleIntakeExtension();
    public abstract boolean doToggleIntakeForward();
    public abstract boolean doToggleIntakeReverse();

    public abstract boolean doToggleShooter();
    public abstract boolean doToggleIndexerIntakeMode();
    public abstract boolean doToggleIndexerShooterMode();
    public abstract boolean doToggleIndexerOmniReverseMode();

    public abstract boolean doToggleTurretAutoAlign();
    public abstract boolean doTurnTurretClockwise();
    public abstract boolean doTurnTurretCounterclockwise();

}

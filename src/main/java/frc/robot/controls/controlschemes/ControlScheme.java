package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();
    public abstract boolean doToggleGearing();

    public abstract boolean doToggleIntakeDown();
    public abstract boolean doToggleIntakeForward();
    public abstract boolean doToggleIntakeReverse();

    public abstract boolean doToggleShooter();

    public abstract boolean doToggleTurretAutoAlign();
    public abstract boolean doTurnTurretClockwise();
    public abstract boolean doTurnTurretCounterclockwise();

}

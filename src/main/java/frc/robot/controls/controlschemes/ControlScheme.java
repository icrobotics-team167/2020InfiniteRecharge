package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    public abstract double getTankLeftSpeed();
    public abstract double getTankRightSpeed();

    public abstract boolean doToggleIntakeExtended();
    public abstract boolean doToggleIntakeForward();
    public abstract boolean doToggleIntakeReverse();

    public abstract boolean doToggleShooter();

    public abstract boolean doToggleTurretAutoAlign();
    public abstract double getTurretClockwiseSpeed();
    public final boolean doTurnTurretClockwise() {
        return getTurretClockwiseSpeed() > 0;
    }
    public abstract double getTurretCounterclockwiseSpeed();
    public final boolean doTurnTurretCounterclockwise() {
        return getTurretCounterclockwiseSpeed() > 0;
    }

}

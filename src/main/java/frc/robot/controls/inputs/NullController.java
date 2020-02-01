package frc.robot.controls.inputs;

public class NullController implements ControlScheme {

    @Override
    public double getTankLeftSpeed() {
        return 0;
    }

    @Override
    public double getTankRightSpeed() {
        return 0;
    }

    @Override
    public boolean doRunIntakeForward() {
        return false;
    }

    @Override
    public boolean doRunIntakeReverse() {
        return false;
    }

    @Override
    public boolean doRunShooter() {
        return false;
    }

    @Override
    public boolean doToggleTurretAutoAlign() {
        return false;
    }

    @Override
    public boolean doTurnTurretClockwise() {
        return false;
    }

    @Override
    public boolean doTurnTurretCounterclockwise() {
        return false;
    }

}

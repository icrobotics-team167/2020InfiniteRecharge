package frc.robot.controls.controlschemes;

public class NullController extends ControlScheme {

    @Override
    public double getTankLeftSpeed() {
        return 0;
    }

    @Override
    public double getTankRightSpeed() {
        return 0;
    }

    @Override
    public boolean doSwitchHighGear() {
        return false;
    }

    @Override
    public boolean doSwitchLowGear() {
        return false;
    }

    @Override
    public boolean doToggleIntakeExtension() {
        return false;
    }

    @Override
    public boolean doToggleIntakeForward() {
        return false;
    }

    @Override
    public boolean doToggleIntakeReverse() {
        return false;
    }

    @Override
    public boolean doToggleShooter() {
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

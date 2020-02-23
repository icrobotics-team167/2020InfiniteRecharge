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
    public double getIntakeSpeed() {
        return 0;
    }

    @Override
    public boolean doToggleIndexerAlignMode() {
        return false;
    }

    @Override
    public boolean doIndexerShooterMode() {
        return false;
    }

    @Override
    public double getIndexerSpeed() {
        return 0;
    }

    @Override
    public boolean doLiftMotorForward() {
        return false;
    }

    @Override
    public boolean doLiftMotorReverse() {
        return false;
    }

    @Override
    public boolean doToggleShooter() {
        return false;
    }

    @Override
    public boolean doAutoAlignTurret() {
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

    @Override
    public boolean doGroundIntake() {
        return false;
    }

    @Override
    public boolean doHumanPlayerIntake() {
        return false;
    }
    
}

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
    public boolean doStraightDrive() {
        return false;
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
    public boolean doToggleGearing() {
        return false;
    }

    @Override
    public boolean doToggleIntakeExtension() {
        return false;
    }

    @Override
    public boolean doGroundIntake() {
        return false;
    }

    @Override
    public boolean doToggleGroundIntakeExtension() {
        return false;
    }

    @Override
    public boolean doHumanPlayerIntake() {
        return false;
    }

    @Override
    public boolean doToggleHumanPlayerIntakeRetraction() {
        return false;
    }

    @Override
    public boolean doRunIntakeManually() {
        return false;
    }

    @Override
    public double getIntakeManualSpeed() {
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
    public boolean doIndexerSickoShootMode() {
        return false;
    }

    @Override
    public boolean doRunIndexerManually() {
        return false;
    }

    @Override
    public double getIndexerManualSpeed() {
        return 0;
    }

    @Override
    public boolean doLiftMotorForwardManually() {
        return false;
    }

    @Override
    public boolean doLiftMotorReverseManually() {
        return false;
    }

    @Override
    public boolean doAntiJamServoReverseManually() {
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
    public boolean doToggleClimbExtension() {
        return false;
    }

    @Override
    public boolean doClimbUp() {
        return false;
    }

    @Override
    public boolean doClimbDown() {
        return false;
    }

    @Override
    public boolean doClimbReset() {
        return false;
    }

}

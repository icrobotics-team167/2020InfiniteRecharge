package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

public class DoubleController extends ControlScheme {

    private Controller primary;
    private Controller secondary;

    public DoubleController(Controller primary, Controller secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getRightStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public boolean doSwitchHighGear() {
        return primary.getRightStickButton();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getLeftStickButton();
    }

    @Override
    public boolean doToggleIntakeExtension() {
        return primary.getBButtonToggled();
    }

    @Override
    public boolean doToggleIntakeForward() {
        return secondary.getAButtonToggled();
    }

    @Override
    public boolean doToggleIntakeReverse() {
        return secondary.getBButtonToggled();
    }

    @Override
    public boolean doToggleIndexerAlignMode() {
        return secondary.getLeftTriggerToggled();
    }

    @Override
    public boolean doToggleIndexerShooterMode() {
        return primary.getRightTriggerToggled();
    }

    @Override
    public boolean doToggleIndexerForward() {
        return secondary.getLeftBumperToggled();
    }

    @Override
    public boolean doToggleIndexerReverse() {
        return secondary.getRightBumperToggled();
    }

    @Override
    public boolean doToggleLiftMotorForward() {
        return secondary.getXButtonToggled();
    }

    @Override
    public boolean doToggleLiftMotorReverse() {
        return secondary.getYButtonToggled();
    }

    @Override
    public boolean doToggleShooter() {
        return secondary.getRightTriggerToggled();
    }

    @Override
    public boolean doToggleTurretAutoAlign() {
        return primary.getAButtonToggled();
    }

    @Override
    public boolean doTurnTurretClockwise() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doTurnTurretCounterclockwise() {
        return primary.getLeftBumper();
    }

}

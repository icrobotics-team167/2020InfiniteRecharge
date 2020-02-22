package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

public class SingleController extends ControlScheme {

    private Controller primary;

    public SingleController(Controller controller) {
        primary = controller;
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
        return primary.getAButtonToggled();
    }

    @Override
    public boolean doToggleIntakeReverse() {
        return primary.getYButtonToggled();
    }

    @Override
    public boolean doToggleIndexerAlignMode() {
        return primary.getXButtonToggled();
    }

    @Override
    public boolean doToggleIndexerShooterMode() {
        return primary.getMenuButtonToggled();
    }

    @Override
    public boolean doToggleIndexerForward() {
        return false;
    }

    @Override
    public boolean doToggleIndexerReverse() {
        return false;
    }

    @Override
    public boolean doToggleLiftMotorForward() {
        return false;
    }

    @Override
    public boolean doToggleLiftMotorReverse() {
        return false;
    }

    @Override
    public boolean doToggleShooter() {
        return primary.getRightTriggerToggled();
    }

    @Override
    public boolean doAutoAlignTurret() {
        return primary.getAButton();
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

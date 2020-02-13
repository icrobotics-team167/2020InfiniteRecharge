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
    public boolean doToggleGearing() {
        return primary.getBButtonToggled();
    }

    @Override
    public boolean doToggleIntakeExtended() {
        return primary.getXButtonToggled();
    }

    @Override
    public boolean doToggleIntakeForward() {
        return primary.getLeftBumperToggled();
    }

    @Override
    public boolean doToggleIntakeReverse() {
        return primary.getYButtonToggled();
    }

    @Override
    public boolean doToggleShooter() {
        return primary.getRightBumperToggled();
    }

    @Override
    public boolean doToggleTurretAutoAlign() {
        return primary.getAButtonToggled();
    }

    @Override
    public double getTurretClockwiseSpeed() {
        double speed = primary.getRightTriggerValue();
        if (Config.Settings.TURRET_TURN_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TURRET_TURN_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return 0.3 * speed;
    }

    @Override
    public double getTurretCounterclockwiseSpeed() {
        double speed = primary.getLeftTriggerValue();
        if (Config.Settings.TURRET_TURN_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TURRET_TURN_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return 0.3 * speed;
    }

}

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
    public double getIntakeSpeed() { 
        if (secondary.getRightStickY() < 0.2) return 0;
        return secondary.getRightStickY() * 0.5;
    }

    @Override
    public boolean doToggleIndexerAlignMode() {
        return secondary.getLeftTriggerToggled();
    }

    @Override
    public boolean doIndexerShooterMode() {
        return primary.getRightTrigger();
    }

    @Override
    public double getIndexerSpeed() {
        if (secondary.getLeftStickX() < 0.2) return 0;
        return secondary.getLeftStickX() * 0.5;
    }

    @Override
    public boolean doLiftMotorForward() {
        return secondary.getXButton();
    }

    @Override
    public boolean doLiftMotorReverse() {
        return secondary.getYButton();
    }

    @Override
    public boolean doToggleShooter() {
        return secondary.getRightTriggerToggled();
    }

    @Override
    public boolean doAutoAlignTurret() {
        return primary.getLeftTrigger();
    }

    @Override
    public boolean doTurnTurretClockwise() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doTurnTurretCounterclockwise() {
        return primary.getLeftBumper();
    }

    @Override
    public boolean doGroundIntake() {
        return secondary.getRightTrigger();
    }

    @Override
    public boolean doHumanPlayerIntake() {
        return secondary.getLeftTrigger();
    }

}

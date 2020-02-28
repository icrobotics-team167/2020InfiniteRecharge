package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

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
        return primary.getRightStickButtonToggled();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getLeftStickButtonToggled();
    }

    @Override
    public boolean doToggleGearing() {
        return false;
    }

    @Override
    public boolean doToggleIntakeExtension() {
        return primary.getBButtonToggled();
    }

    @Override
    public boolean doGroundIntake() {
        return secondary.getRightTrigger();
    }

    @Override
    public boolean doToggleGroundIntakeExtension() {
        return secondary.getRightTriggerToggled();
    }

    @Override
    public boolean doHumanPlayerIntake() {
        return secondary.getLeftTrigger();
    }

    @Override
    public boolean doToggleHumanPlayerIntakeRetraction() {
        return secondary.getLeftTriggerToggled();
    }

    @Override
    public boolean doRunIntakeManually() {
        return getIntakeManualSpeed() != 0;
    }

    @Override
    public double getIntakeManualSpeed() {
        if (!Config.Settings.INTAKE_DEAD_ZONE_ENABLED) {
            return 0.5 * secondary.getRightStickY();
        }
        double speed = secondary.getRightStickY();
        double deadZoneSize = Math.abs(Config.Tolerances.INTAKE_DEAD_ZONE_SIZE);
        if (Math.abs(speed) < deadZoneSize) {
            return 0;
        } else if (speed > 0) {
            return ((0.5 * speed) / (1 - deadZoneSize)) - ((0.5 * deadZoneSize) / (1 - deadZoneSize));
        } else {
            return ((0.5 * speed) / (1 - deadZoneSize)) + ((0.5 * deadZoneSize) / (1 - deadZoneSize));
        }
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
    public boolean doRunIndexerManually() {
        return getIndexerManualSpeed() != 0;
    }

    @Override
    public double getIndexerManualSpeed() {
        if (!Config.Settings.INDEXER_DEAD_ZONE_ENABLED) {
            return 0.5 * secondary.getLeftStickX();
        }
        double speed = secondary.getLeftStickX();
        double deadZoneSize = Math.abs(Config.Tolerances.INDEXER_DEAD_ZONE_SIZE);
        if (Math.abs(speed) < deadZoneSize) {
            return 0;
        } else if (speed > 0) {
            return ((0.5 * speed) / (1 - deadZoneSize)) - ((0.5 * deadZoneSize) / (1 - deadZoneSize));
        } else {
            return ((0.5 * speed) / (1 - deadZoneSize)) + ((0.5 * deadZoneSize) / (1 - deadZoneSize));
        }
    }

    @Override
    public boolean doLiftMotorForwardManually() {
        return secondary.getXButton();
    }

    @Override
    public boolean doLiftMotorReverseManually() {
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
    public boolean doToggleClimbExtension() {
        if (primary.isPSController()) {
            return ((PSController) primary).getTouchpadButtonToggled();
        }
        return false;
    }

    @Override
    public boolean doClimbUp() {
        return primary.getRightTrigger();
    }

    @Override
    public boolean doClimbDown() {
        return primary.getLeftTrigger();
    }

    @Override
    public boolean doClimbReset() {
        if (primary.isPSController()) {
            return ((PSController) primary).getPSButton();
        }
        return false;
    }

}

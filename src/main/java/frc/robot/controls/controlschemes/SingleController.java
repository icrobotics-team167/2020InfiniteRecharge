package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

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
    public boolean doToggleGearing() {
        return false;
    }

    @Override
    public boolean doToggleIntakeExtension() {
        return primary.getBButtonToggled();
    }

    @Override
    public boolean doGroundIntake() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doToggleGroundIntakeExtension() {
        return primary.getRightBumperToggled();
    }

    @Override
    public boolean doHumanPlayerIntake() {
        return primary.getLeftBumper();
    }

    @Override
    public boolean doToggleHumanPlayerIntakeRetraction() {
        return primary.getLeftBumperToggled();
    }

    @Override
    public boolean doRunIntakeManually() {
        return getIntakeManualSpeed() != 0;
    }

    @Override
    public double getIntakeManualSpeed() {
        return 0;
    }

    @Override
    public boolean doToggleIndexerAlignMode() {
        return primary.getXButtonToggled();
    }

    @Override
    public boolean doIndexerShooterMode() {
        return primary.getRightTrigger();
    }
    
    @Override
    public boolean doIndexerSickoShootMode() {
        return false;
    }

    @Override
    public boolean doAntiJamReverse() {
        return false;
    }

    @Override
    public boolean doRunIndexerManually() {
        return getIndexerManualSpeed() != 0;
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
    public boolean doToggleShooter() {
        return primary.getYButtonToggled();
    }

    @Override
    public boolean doAutoAlignTurret() {
        return primary.getAButton();
    }

    @Override
    public boolean doTurnTurretClockwise() {
        return primary.getMenuButton();
    }

    @Override
    public boolean doTurnTurretCounterclockwise() {
        return primary.getViewButton();
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

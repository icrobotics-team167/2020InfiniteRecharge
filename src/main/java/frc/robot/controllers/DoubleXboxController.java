package frc.robot.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Config;

public class DoubleXboxController implements Controller {

    private XboxController primary = new XboxController(Config.Ports.PRIMARY_CONTROLLER);
    private XboxController secondary = new XboxController(Config.Ports.SECONDARY_CONTROLLER);

    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getY(GenericHID.Hand.kLeft);
        if (Config.Tolerances.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) <= Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            return 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getY(GenericHID.Hand.kRight);
        if (Config.Tolerances.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) <= Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            return 0;
        }
        return speed;
    }

    @Override
    public double getSwerveHorizontalSpeed() {
        double speed = primary.getX(GenericHID.Hand.kLeft);
        if (Config.Tolerances.SWERVE_MOVE_DEAD_ZONE_ENABLED && Math.abs(speed) <= Math.abs(Config.Tolerances.SWERVE_MOVE_DEAD_ZONE_SIZE)) {
            return 0;
        }
        return speed;
    }

    @Override
    public double getSwerveVerticalSpeed() {
        double speed = primary.getY(GenericHID.Hand.kLeft);
        if (Config.Tolerances.SWERVE_MOVE_DEAD_ZONE_ENABLED && Math.abs(speed) <= Math.abs(Config.Tolerances.SWERVE_MOVE_DEAD_ZONE_SIZE)) {
            return 0;
        }
        return speed;
    }

    @Override
    public double getSwerveTurnSpeed() {
        double speed = primary.getX(GenericHID.Hand.kRight);
        if (Config.Tolerances.SWERVE_SPIN_DEAD_ZONE_ENABLED && Math.abs(speed) <= Math.abs(Config.Tolerances.SWERVE_SPIN_DEAD_ZONE_SIZE)) {
            return 0;
        }
        return speed;
    }

}

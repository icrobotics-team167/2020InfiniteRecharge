package frc.robot.routines.teleop;

import frc.robot.controllers.Controller;
import frc.robot.subsystems.SwerveDriveBase;

public class SwerveTeleop extends Teleop {

    SwerveDriveBase swerveDriveBase;

    public SwerveTeleop(Controller controller) {
        super(controller);
        swerveDriveBase = SwerveDriveBase.getInstance();
    }

    @Override
    public void periodic() {
        swerveDriveBase.swerveDrive(controller.getSwerveHorizontalSpeed(), controller.getSwerveVerticalSpeed(), controller.getSwerveAngularSpeed());
    }

}

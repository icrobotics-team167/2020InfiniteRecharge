package frc.robot.routines.teleop;

import frc.robot.controllers.Controller;
import frc.robot.subsystems.TankDriveBase;

public class TankTeleop extends Teleop {

    TankDriveBase tankDriveBase;

    public TankTeleop(Controller controller) {
        super(controller);
        tankDriveBase = TankDriveBase.getInstance();
    }

    @Override
    public void periodic() {
        tankDriveBase.tankDrive(controller.getTankLeftSpeed(), controller.getTankRightSpeed());
    }

}

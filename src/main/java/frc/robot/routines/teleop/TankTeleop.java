package frc.robot.routines.teleop;

import frc.robot.controls.inputs.ControlScheme;
import frc.robot.subsystems.drive.SparkTankDriveBase;

public class TankTeleop extends Teleop {

    SparkTankDriveBase tankDriveBase;

    public TankTeleop(ControlScheme controller) {
        super(controller);
        tankDriveBase = SparkTankDriveBase.getInstance();
    }

    @Override
    public void periodic() {
        tankDriveBase.tankDrive(controller.getTankLeftSpeed(), controller.getTankRightSpeed());
    }

}

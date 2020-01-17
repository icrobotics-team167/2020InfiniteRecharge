package frc.robot.routines.teleop;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.Controller;
import frc.robot.subsystems.SwerveDriveBase;

public class SwerveTeleop extends Teleop {

    SwerveDriveBase swerveDriveBase;

    Encoder shooterEncoder;

    public SwerveTeleop(Controller controller) {
        super(controller);
        swerveDriveBase = SwerveDriveBase.getInstance();
        swerveDriveBase.resetEncoders();

        shooterEncoder = new Encoder(6, 7, 8);
    }

    boolean done = false;
    @Override
    public void periodic() {
        // swerveDriveBase.swerveDrive(controller.getSwerveHorizontalSpeed(), controller.getSwerveVerticalSpeed(), controller.getSwerveAngularSpeed());
        // if (!done) {
        //     done = swerveDriveBase.driveMeter();
        // } else {
        //     swerveDriveBase.stop();
        // }

        swerveDriveBase.printEncoderValues();

        SmartDashboard.putNumber("Shooter Encoder", shooterEncoder.get());
    }

}

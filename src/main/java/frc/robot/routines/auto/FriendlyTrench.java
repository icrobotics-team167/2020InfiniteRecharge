package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

// Quincy does stuff lo



public class FriendlyTrench extends Action {

    public String trajectoryJSON;
    public Trajectory trajectory;

    public State initialPoint;
    public double previousTime;
    public PeriodicTimer timer;

    private Supplier<Pose2d> pose; // do odometry
    private RamseteController follower; 
    private DifferentialDriveKinematics kinematics; 

    private DifferentialDriveOdometry odometry;


    @Override
    public void init() {
        follower = new RamseteController();

        kinematics = new DifferentialDriveKinematics(2);

        odometry = new DifferentialDriveOdometry(Subsystems.driveBase.getGyroHeading(), trajectory.getInitialPose());

        trajectoryJSON = "paths/FTR.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }

        timer = new PeriodicTimer();

        initialPoint = trajectory.sample(0);
        previousTime = 0;

        timer.reset();

        pose = new Supplier<Pose2d>() {
            public Pose2d get() {
                return new Pose2d();
            }
        };
    }
    
    @Override
    public void periodic() {
        odometry.update(Subsystems.driveBase.getGyroHeading(), Subsystems.driveBase.getLeftSpeed(), Subsystems.driveBase.getRightSpeed());

        double currentTime = timer.get();

        DifferentialDriveWheelSpeeds targetWheelSpeeds = kinematics.toWheelSpeeds(follower.calculate(
            pose.get(), 
            trajectory.sample(currentTime))
        );

        double leftSpeed = targetWheelSpeeds.leftMetersPerSecond;
        double rightSpeed = targetWheelSpeeds.rightMetersPerSecond;

        Subsystems.driveBase.setReferences(leftSpeed, rightSpeed);
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }

    @Override
    public void done() {
        timer.reset();
    }

}

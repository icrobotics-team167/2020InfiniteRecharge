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
import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

public class Auto extends Action {

    private String trajectoryJSON;
    private Trajectory trajectory;

    private PeriodicTimer timer;
    private Supplier<Pose2d> pose;
    private RamseteController follower; 
    private DifferentialDriveKinematics kinematics; 

    private DifferentialDriveOdometry odometry;

    public Auto(AutoRoutine routine) {
        trajectoryJSON = "paths/" + routine.trajectoryFile + ".wpilib.json";

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            setState(AutoState.READY);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
            setState(AutoState.FAILED);
        }
    }

    @Override
    public void init() {
        follower = new RamseteController();
        kinematics = new DifferentialDriveKinematics(2);
        odometry = new DifferentialDriveOdometry(Subsystems.driveBase.getGyroHeading(), trajectory.getInitialPose());

        timer = new PeriodicTimer();

        timer.reset();

        pose = new Supplier<Pose2d>() {
            public Pose2d get() {
                return odometry.getPoseMeters();
            }
        };
    }
    
    @Override
    public void periodic() {
        odometry.update(Subsystems.driveBase.getGyroHeading(), Subsystems.driveBase.getLeftSpeed(), Subsystems.driveBase.getRightSpeed());

        DifferentialDriveWheelSpeeds targetWheelSpeeds = kinematics.toWheelSpeeds(follower.calculate(
            pose.get(), 
            trajectory.sample(timer.get()))
        );
        
        Subsystems.driveBase.setReferences(targetWheelSpeeds.leftMetersPerSecond, targetWheelSpeeds.rightMetersPerSecond);
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

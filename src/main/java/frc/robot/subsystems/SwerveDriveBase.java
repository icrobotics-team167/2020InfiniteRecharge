package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.EncoderType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.Config;

public class SwerveDriveBase {

    private AHRS navx;
    private SwerveDriveKinematics swerveKinematics;
    private CANSparkMax frontLeftSpin;
    private CANSparkMax frontLeftMove;
    private CANSparkMax frontRightSpin;
    private CANSparkMax frontRightMove;
    private CANSparkMax backLeftSpin;
    private CANSparkMax backLeftMove;
    private CANSparkMax backRightSpin;
    private CANSparkMax backRightMove;
    private CANEncoder frontLeftSpinEncoder;
    private CANEncoder frontLeftMoveEncoder;
    private CANEncoder frontRightSpinEncoder;
    private CANEncoder frontRightMoveEncoder;
    private CANEncoder backLeftSpinEncoder;
    private CANEncoder backLeftMoveEncoder;
    private CANEncoder backRightSpinEncoder;
    private CANEncoder backRightMoveEncoder;

    public SwerveDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(Config.Measures.Swerve.FRONT_LEFT_X, Config.Measures.Swerve.FRONT_LEFT_Y),
            new Translation2d(Config.Measures.Swerve.FRONT_RIGHT_X, Config.Measures.Swerve.FRONT_RIGHT_Y),
            new Translation2d(Config.Measures.Swerve.BACK_LEFT_X, Config.Measures.Swerve.BACK_LEFT_Y),
            new Translation2d(Config.Measures.Swerve.BACK_RIGHT_X, Config.Measures.Swerve.BACK_RIGHT_Y)
        );

        frontLeftSpin = new CANSparkMax(Config.Ports.Swerve.FRONT_LEFT_SPIN, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontLeftMove = new CANSparkMax(Config.Ports.Swerve.FRONT_LEFT_MOVE, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRightSpin = new CANSparkMax(Config.Ports.Swerve.FRONT_RIGHT_SPIN, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRightMove = new CANSparkMax(Config.Ports.Swerve.FRONT_RIGHT_MOVE, CANSparkMaxLowLevel.MotorType.kBrushless);
        backLeftSpin = new CANSparkMax(Config.Ports.Swerve.BACK_LEFT_SPIN, CANSparkMaxLowLevel.MotorType.kBrushless);
        backLeftMove = new CANSparkMax(Config.Ports.Swerve.BACK_LEFT_MOVE, CANSparkMaxLowLevel.MotorType.kBrushless);
        backRightSpin = new CANSparkMax(Config.Ports.Swerve.BACK_RIGHT_SPIN, CANSparkMaxLowLevel.MotorType.kBrushless);
        backRightMove = new CANSparkMax(Config.Ports.Swerve.BACK_RIGHT_MOVE, CANSparkMaxLowLevel.MotorType.kBrushless);

        frontLeftSpinEncoder = frontLeftSpin.getEncoder(EncoderType.kQuadrature, 4096);
        frontLeftMoveEncoder = frontLeftMove.getEncoder(EncoderType.kQuadrature, 4096);
        frontRightSpinEncoder = frontRightSpin.getEncoder(EncoderType.kQuadrature, 4096);
        frontRightMoveEncoder = frontRightMove.getEncoder(EncoderType.kQuadrature, 4096);
        backLeftSpinEncoder = backLeftSpin.getEncoder(EncoderType.kQuadrature, 4096);
        backLeftMoveEncoder = backLeftMove.getEncoder(EncoderType.kQuadrature, 4096);
        backRightSpinEncoder = backRightSpin.getEncoder(EncoderType.kQuadrature, 4096);
        backRightMoveEncoder = backRightMove.getEncoder(EncoderType.kQuadrature, 4096);

        frontLeftSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontLeftMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontRightSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        frontRightMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backLeftSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backLeftMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backRightSpin.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backRightMove.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void swerveDrive(double horizontalSpeed, double verticalSpeed, double angularSpeed) {
        ChassisSpeeds speeds = new ChassisSpeeds(
                verticalSpeed * Config.Measures.SwerveSpeeds.MOVE,
                horizontalSpeed * Config.Measures.SwerveSpeeds.MOVE,
                angularSpeed * Config.Measures.SwerveSpeeds.SPIN
        );

        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds);
        SwerveModuleState frontLeftState = moduleStates[0];
        SwerveModuleState frontRightState = moduleStates[1];
        SwerveModuleState backLeftState = moduleStates[2];
        SwerveModuleState backRightState = moduleStates[3];

        // TODO drive modules at correct speeds
    }

}
